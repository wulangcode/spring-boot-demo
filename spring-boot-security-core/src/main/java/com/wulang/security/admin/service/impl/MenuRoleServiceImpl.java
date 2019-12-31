package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.MenuRoleDao;
import com.wulang.security.admin.entity.collection.MenuRole;
import com.wulang.security.admin.service.MenuRoleService;
import com.wulang.security.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: wulang
 * @date: 2019/11/26
 * @description:
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleDao, MenuRole> implements MenuRoleService {

    @Autowired
    private MenuRoleDao menuRoleDao;

    @Override
    public boolean updateRoleAuth(Integer roleId, List<Integer> authIds) {
        menuRoleDao.delete(new UpdateWrapper<MenuRole>().eq("role_id", roleId));
        if (authIds != null && authIds.size() > 0) {
            if (menuRoleDao.insertRoleAuths(roleId, authIds) < authIds.size()) {
                throw new BusinessException("操作失败");
            }
        }
        return true;
    }
}
