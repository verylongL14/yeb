package com.jzq.server.controller;


import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Department;
import com.jzq.server.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  职位管理
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Api(tags = "部门类接口管理")
@RestController
@RequestMapping("/api/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;


    @ApiOperation(value = "获取所有职位")
    @GetMapping("/")
    public List<Department> getAllDepartment(){
        return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "添加一个部门")
    @PostMapping("/")
    public RespBean addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }

    @ApiOperation(value = "删除一个部门")
    @DeleteMapping("/{id}")
    public RespBean deleteDepartment(@PathVariable Integer id) {
        return departmentService.deleteDepartment(id);
    }

}
