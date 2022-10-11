package com.jzq.server.service;

import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzq.server.pojo.RespPageBean;
import io.swagger.models.auth.In;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取员工（分页）
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    RespPageBean getEmployee(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取最大的工号
     * @return
     */
    RespBean maxWorkID();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    RespBean addEmp(Employee employee);

    /**
     *  查询员工（不传就是查所有）
     * @param id
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有员工账套
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
