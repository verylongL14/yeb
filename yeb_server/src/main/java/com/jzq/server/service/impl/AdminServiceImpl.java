package com.jzq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzq.server.util.result.RespBean;
import com.jzq.server.config.security.JwtTokenUtil;
import com.jzq.server.mapper.AdminRoleMapper;
import com.jzq.server.mapper.RoleMapper;
import com.jzq.server.pojo.Admin;
import com.jzq.server.mapper.AdminMapper;
import com.jzq.server.pojo.AdminRole;
import com.jzq.server.pojo.Role;
import com.jzq.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzq.server.util.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;   // 判断密码是否一致
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")    // 根据value注解拿到yml中设置的token头
    private String tokenHead;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    /**
     * 登录成功返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        // 验证一下验证码是否正确
        // 在Session中拿到验证码
        Object captcha = request.getSession().getAttribute("captcha");

        System.out.println("captcha"+captcha);
        // 验证码暂时关闭
        if (StringUtils.isEmpty(captcha) || !captcha.equals(code)) {
           // 在登录认证前先对比验证是否一样 (验证码为空， 或者与生成的不一样的话)
           return RespBean.warning("验证码输入错误,请重新输入！");
        }


        // 登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);  // （查库）

        if (userDetails!=null && !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.warning("用户名或者密码不正确");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.warning("账号被禁用，请练习管理员");
        }

        // 更新security登录用户对象
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());//第二个参数为口令(密码)不用传
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功", tokenMap);
    }

    /**
     * 获取当前用户信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
    }

    /**
     * 根据用户id获取角色权限
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    /**
     * 获取所有的操作员
     * @param keywords
     * @return
     */
    @Override
    public List<Admin> getAllAdmins(String keywords) {
        // 不可以查当前已经登录的操作员id记录
        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(), keywords);

    }

    /**
     * 修改管理员权限
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        // 删除所有的adminId的权限
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));

        if (rids == null || rids.length == 0) {
            return RespBean.warning("用户角色全部删除成功！");
        }
        // 添加所有的传过来的权限
        Integer integer = adminRoleMapper.addAdminRole(adminId, rids);
        if (integer > 0) {
            return RespBean.success("修改管理员角色成功");
        }
        return RespBean.warning("修改管理员角色失败");
    }

    /**
     * 更新用户密码
     * @param oldPass
     * @param pass
     * @param adminId
     * @return
     */
    @Override
    public RespBean updateAdminPassword(String oldPass, String pass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encodery = new BCryptPasswordEncoder();
        // 判断旧密码是否正确
        if (encodery.matches(oldPass, admin.getPassword())) {
            // 加密
            admin.setPassword(encodery.encode(pass));
            int result = adminMapper.updateById(admin);
            if (1 == result) {
                return RespBean.success("更新密码成功!");
            }
        }
        return RespBean.warning("更新密码失败!");
    }

    /**
     * 更新用户头像
     * @param url
     * @param id
     * @param authentication
     * @return
     */
    @Override
    public RespBean updateAdminUserFace(String url, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(url);

        // 更新数据库
        int result = adminMapper.updateById(admin);
        if (1==result) {
            //更新数据库成功后要更新全局对象（security）
            Admin principal = (Admin) authentication.getPrincipal();
            principal.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            return RespBean.success("更新用户头像成功");
        }

        return RespBean.warning("更新用户头像失败");
    }
}
