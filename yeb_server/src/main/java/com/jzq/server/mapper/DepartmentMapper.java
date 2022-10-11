package com.jzq.server.mapper;

import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门
     * @param parentId
     * @return
     */
    List<Department> getAllDepartment(Integer parentId);

    /**
     * 添加一个部门
     * @param department
     * @return
     */
    RespBean addDepartment(Department department);

    /**
     * 删除一个部门
     * @param department
     */
    void deleteDepartment(Department department);
}
