package com.jzq.server.service.impl;

import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Department;
import com.jzq.server.mapper.DepartmentMapper;
import com.jzq.server.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门
     * @return
     */
    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment(-1);
    }

    /**
     * 添加一个部门
     * @param department
     * @return
     */
    @Override
    public RespBean addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
        if (department.getResult() == 1) {
            return RespBean.success("部门添加成功!");
        }
        return RespBean.warning("部门添加失败!");
    }

    /**
     * 删除一个部门
     * @param id
     * @return
     */
    @Override
    public RespBean deleteDepartment(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        if (department.getResult() == -2) {
            return RespBean.warning("该部门下有子部门，删除失败");
        }
        if (department.getResult() == -1) {
            return RespBean.warning("该部门下有员工，删除失败");
        }
        if (department.getResult() == 1) {
            return RespBean.success("删除部门成功");
        }
        return RespBean.warning("删除部门失败");
    }
}
