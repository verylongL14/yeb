package com.jzq.server.mapper;

import com.jzq.server.pojo.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 添加admin权限
     * @param adminId
     * @param rids
     * @return
     */
    Integer addAdminRole(Integer adminId, Integer[] rids);
}
