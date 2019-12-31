package com.wulang.security.distributed.uaa.model;

import lombok.Data;

/**
 * @author wulang
 * @create 2019/12/21/13:56
 */
@Data
public class PermissionDto {

    private String id;
    private String code;
    private String description;
    private String url;
}
