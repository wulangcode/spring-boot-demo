package com.wulang.security.admin.entity.collection;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuMeta implements Serializable {

    private boolean keepAlive;
    private boolean requireAuth;

}
