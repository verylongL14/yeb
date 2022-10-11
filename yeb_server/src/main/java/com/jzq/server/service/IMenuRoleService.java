package com.jzq.server.service;

import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色权限
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
