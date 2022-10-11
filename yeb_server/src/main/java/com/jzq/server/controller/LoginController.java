package com.jzq.server.controller;

import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Admin;
import com.jzq.server.pojo.AdminLoginParam;
import com.jzq.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录
 */


@Api(tags = "登录类接口管理")
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) {

        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(),adminLoginParam.getCode(), request);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public RespBean getAdminInfo(Principal principal) {
        // principal是我们设置到security中存的全局的用户信息
        if (principal == null) {
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        // 设置一下权限
        admin.setRoles(adminService.getRoles(admin.getId()));

        return RespBean.success("查询成功",admin);
    }


    @ApiOperation(value = "用户退出登录")
    @PostMapping("/logout")
    private RespBean logout(){
        return RespBean.success("注销成功!");
    }
}
