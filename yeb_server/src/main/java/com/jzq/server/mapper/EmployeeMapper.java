package com.jzq.server.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jzq.server.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工（分页）
     * @param page
     * @param employee
     * @param beginDateScope
     */

    IPage<Employee> getEmployeeByPage(Page<Employee> page,   // 查询前获取到加入分页（aop）
                      @Param("employee") Employee employee,
                      @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     *  查询员工（不传就是查所有）
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有员工账套
     * @param page
     * @return
     */
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
