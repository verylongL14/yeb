package com.jzq.server.controller;


import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Admin;
import com.jzq.server.pojo.Role;
import com.jzq.server.service.IAdminService;
import com.jzq.server.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Api(tags = "管理员接口管理")
@RestController
@RequestMapping("/api/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;


    @ApiOperation(value = "获取所有的管理员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keywords) {
        return adminService.getAllAdmins(keywords);
    }


    @ApiOperation(value = "更新管理员信息")
    @PutMapping("/")
    public RespBean putAdmin(@RequestBody Admin admin) {
        if (adminService.updateById(admin)) {
            return RespBean.success("管理员信息更新成功");
        }
        return RespBean.warning("管理员信息更新失败");
    }

    @ApiOperation(value = "删除管理员信息")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id) {
        if (adminService.removeById(id)) {
            return RespBean.success("删除管理员成功");
        }
        return RespBean.warning("删除管理员失败");
    }

    @ApiOperation(value = "获取所有的角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "修改管理员角色")
    @PutMapping("/role")
    public RespBean putRole(Integer adminId, Integer[] rids) {
        return adminService.updateAdminRole(adminId, rids);
    }

}
