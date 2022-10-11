package com.jzq.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Menu;
import com.jzq.server.pojo.MenuRole;
import com.jzq.server.pojo.Role;
import com.jzq.server.service.IMenuRoleService;
import com.jzq.server.service.IMenuService;
import com.jzq.server.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 */
@Api(tags = "权限接口管理")
@RestController
@RequestMapping("/api/system/basic/role")
public class PermissController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "查询所有权限角色")
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "增加一个角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        // 判断角色名字是否规范
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_"+role.getName());
        }

        if (roleService.save(role)) {
            return RespBean.success("增加角色成功");
        }
        return RespBean.warning("增加角色失败");
    }

    @ApiOperation(value = "删除一个角色")
    @DeleteMapping("/role/{id}")
    public RespBean deleteRole(@PathVariable Integer id){
        if (roleService.removeById(id)) {
            return RespBean.success("删除角色成功");
        }
        return RespBean.warning("删除角色失败");
    }


    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByrid(@PathVariable Integer rid) {
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid)).stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        return menuRoleService.updateMenuRole(rid, mids);
    }

}
