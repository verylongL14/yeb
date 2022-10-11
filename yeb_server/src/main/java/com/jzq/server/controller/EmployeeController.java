package com.jzq.server.controller;


import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.*;
import com.jzq.server.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Api(tags = "员工类接口管理")
@RestController
@RequestMapping("/api/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPoliticsStatusService politicsStatusService;

    @Autowired
    private IJoblevelService joblevelService;

    @Autowired
    private INationService nationService;

    @Autowired
    private IPositionService positionService;

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation("获取所有的员工（分页）")
    @GetMapping("/")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Employee employee,
                                    LocalDate[] beginDateScope) {
        return employeeService.getEmployee(currentPage, size, employee, beginDateScope);
    }

    @ApiOperation(value = "获取所有的政治面貌")
    @GetMapping("/politicsStatus")
    public List<PoliticsStatus> getAllPoloticsStatus() {
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所有的职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getAllJoblevel() {
        return joblevelService.list();
    }

    @ApiOperation(value = "获取所有的民族")
    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.list();
    }

    @ApiOperation(value = "获取所有的职位")
    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return positionService.list();
    }

    @ApiOperation(value = "获取所有的部门")
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID() {
        return employeeService.maxWorkID();
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {
        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) {
            return RespBean.success("更新员工成功");
        }
        return RespBean.warning("更新员工失败");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id) {
        if (employeeService.removeById(id)) {
            return RespBean.success("删除员工成功");
        }
        return RespBean.warning("删除员工失败");
    }

}
