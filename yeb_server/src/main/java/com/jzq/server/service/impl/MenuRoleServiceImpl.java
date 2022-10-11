package com.jzq.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.MenuRole;
import com.jzq.server.mapper.MenuRoleMapper;
import com.jzq.server.service.IMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    /**
     * 更新角色权限
     * @param rid
     * @param mids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        // 先删除原来的权限记录
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));

        // 判断是否为删除数据
        if (mids == null || mids.length==0) {
            return RespBean.success("删除所有权限！");
        }
        // 批量增加权限记录
        Integer integer = menuRoleMapper.insertRecord(rid, mids);
        if (integer == mids.length) {
            return RespBean.success("更新角色权限成功!");
        }
        return RespBean.warning("更新角色失败");
    }
}
