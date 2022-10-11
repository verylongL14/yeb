package com.jzq.server.controller;


import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.EmployeeEc;
import com.jzq.server.service.IEmployeeEcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  奖惩管理
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Api(tags = "奖惩类接口管理")
@RestController
@RequestMapping("/api/personnel/ec/employee-ec")
public class EmployeeEcController {

    @Autowired
    private IEmployeeEcService employeeEcService;

    @ApiOperation(value = "获取所有奖惩信息")
    @GetMapping("/")
    public List<EmployeeEc> getAllEmployeeEc() {
        return employeeEcService.list();
    }

    @ApiOperation(value = "添加一条奖惩记录")
    @PostMapping("/")
    public RespBean addEmployeeEc(@RequestBody EmployeeEc employeeEc) {
        employeeEc.setEcDate(LocalDateTime.now());
        if (employeeEcService.save(employeeEc)) {
            return RespBean.success("奖惩记录添加成功");
        }
        return RespBean.warning("奖惩记录添加失败");
    }

    @ApiOperation(value = "修改一条奖惩记录")
    @PutMapping("/")
    public RespBean updateEmployeeEc(@RequestBody EmployeeEc employeeEc) {
        if (employeeEcService.updateById(employeeEc)) {
            return RespBean.success("奖惩记录修改成功");
        }
        return RespBean.warning("奖惩记录修改失败");
    }

    @ApiOperation(value = "删除一条奖惩记录")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeEc(@PathVariable Integer id) {
        if (employeeEcService.removeById(id)) {
            return RespBean.success("成功删除一个奖惩记录");
        }
        return RespBean.warning("删除奖惩记录失败");
    }

    @ApiOperation(value = "删除一组奖惩记录")
    @DeleteMapping("/")
    private RespBean deleteEmployeeEcs(Integer[] ids){
        if (employeeEcService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("成功删除一组奖惩记录");
        }
        return RespBean.warning("删除奖惩记录失败");
    }
}
