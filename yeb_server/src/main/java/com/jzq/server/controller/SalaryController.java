package com.jzq.server.controller;


import com.jzq.server.pojo.Salary;
import com.jzq.server.service.ISalaryService;
import com.jzq.server.util.result.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */

@Api(tags = "工资类接口管理")
@RestController
@RequestMapping("/api/salary/sob/")
public class SalaryController {

    @Autowired
    private ISalaryService salaryService;


    @ApiOperation(value = "获取所有工资模板")
    @GetMapping("/")
    public List<Salary> getAllSalarys() {
        return salaryService.list();
    }

    @ApiOperation(value = "添加一个工资模板")
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary) {
        salary.setCreateDate(LocalDateTime.now());
        if (salaryService.save(salary)) {
            return RespBean.success("添加工资成功");
        }
        return RespBean.warning("添加工资失败");
    }

    @ApiOperation(value = "删除一个工资模板")
    @DeleteMapping("/{id}")
    public RespBean deleteSalary(@PathVariable Integer id) {
        if (salaryService.removeById(id)) {
            return RespBean.success("删除工资成功");
        }
        return RespBean.warning("删除工资失败");
    }

    @ApiOperation(value = "修改一个工资模板")
    @PutMapping("/")
    public RespBean putSalary(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) {
            return RespBean.success("修改工资成功");
        }
        return RespBean.warning("修改工资失败");
    }
}
