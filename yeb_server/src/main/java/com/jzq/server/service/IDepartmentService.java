package com.jzq.server.service;

import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartment();

    /**
     * 添加一个部门
     * @param department
     * @return
     */
    RespBean addDepartment(Department department);

    /**
     * 删除一个部门
     * @param id
     * @return
     */
    RespBean deleteDepartment(Integer id);
}
