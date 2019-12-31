package com.wulang.security.common.utils;

import com.wulang.security.admin.entity.collection.AuthoritiesVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wulang
 * @date: 2019/12/4
 * @description:
 */
@Component
public class TreeUtils {
    public List<AuthoritiesVO> findTree(List<AuthoritiesVO> list) {
        List<AuthoritiesVO> sysDepts = new ArrayList<>();
        for (AuthoritiesVO dept : list) {
            if (dept.getParentId() == null || dept.getParentId() == -1) {
                sysDepts.add(dept);
            }
        }
        findChildren(sysDepts, list);
        return sysDepts;
    }

    private static void findChildren(List<AuthoritiesVO> sysDepts, List<AuthoritiesVO> depts) {
        for (AuthoritiesVO sysDept : sysDepts) {
            List<AuthoritiesVO> children = new ArrayList<>();
            for (AuthoritiesVO dept : depts) {
                if (sysDept.getMenuId() != null && sysDept.getMenuId().equals(dept.getParentId())) {
                    dept.setLabel(dept.getLabel());
                    dept.setLabel(dept.getLabel());
                    children.add(dept);
                }
                dept.setLabel(dept.getLabel());
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }

}
