package com.jzq.server.service.impl;

import com.jzq.server.pojo.Admin;
import com.jzq.server.pojo.Menu;
import com.jzq.server.mapper.MenuMapper;
import com.jzq.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户id获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {
        // 获取 Security全局内的用户信息
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        /**
         *  先不存redis
        // 首先先在redis中获取
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 根据用户id查菜单数据
        List<Menu> menuList = (List<Menu>) valueOperations.get("menu_"+admin.getId());
        System.out.println(menuList);
        // 判断List是否为空，即是判断redis中是否存在数据
        if (CollectionUtils.isEmpty(menuList)) {
            // 如果空的话，在数据库中查找
            menuList = menuMapper.getMenusByAdminId(admin.getId());
            // 设置到redis中
            valueOperations.set("menu_"+admin.getId(), menuList);
        }

         */

        // 通过id查询数据库
        return menuMapper.getMenusByAdminId(admin.getId());
    }

    /**
     * 根据角色查菜单
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
