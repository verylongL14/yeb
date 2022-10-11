package com.jzq.server.mapper;

import com.jzq.server.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 批量增加权限记录
     * @param rid
     * @param mids
     * @return
     */
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
