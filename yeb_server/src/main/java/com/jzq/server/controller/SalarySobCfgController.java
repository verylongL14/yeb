package com.jzq.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jzq.server.pojo.Employee;
import com.jzq.server.pojo.RespPageBean;
import com.jzq.server.pojo.Salary;
import com.jzq.server.service.IEmployeeService;
import com.jzq.server.service.ISalaryService;
import com.jzq.server.util.result.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "员工套账接口管理")
@RestController
@RequestMapping("/api/salary/sobcfg")
public class SalarySobCfgController {
    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IEmployeeService employeeService;


    @ApiOperation(value = "获取所有工资套账")
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries() {
        return salaryService.list();
    }


    @ApiOperation(value = "获取所有员工账套")
    @GetMapping("/")
    public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeeWithSalary(currentPage, size);
    }

    @ApiOperation(value = "更新员工账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid, Integer sid) {
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id", eid))) {
            return RespBean.success("更新员工账套成功");
        }
        return RespBean.warning("更新员工账套失败");
    }
}
