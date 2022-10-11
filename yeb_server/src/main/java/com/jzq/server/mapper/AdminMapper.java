package com.jzq.server.mapper;

import com.jzq.server.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzq.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
public interface AdminMapper extends BaseMapper<Admin> {


    /**
     * 查询所有操作员（除了本账号）
     * @param id
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmins(Integer id, String keywords);
}
