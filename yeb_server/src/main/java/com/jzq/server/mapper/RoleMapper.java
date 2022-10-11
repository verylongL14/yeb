package com.jzq.server.mapper;

import com.jzq.server.pojo.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取角色权限
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
