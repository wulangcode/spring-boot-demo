package com.wulang.security.admin.entity.collection;

import lombok.Data;

import java.util.List;

/**
 * @author: wulang
 * @date: 2019/12/4
 * @description:
 */
@Data
public class AuthoritiesVO {
    private Integer menuId;
    private String label;
    private Integer parentId;
    private Boolean checked;
    private List<AuthoritiesVO> children;
}
