package com.wulang.security.admin.entity.monitor;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 船舶(TbShip)实体类
 *
 * @author makejava
 * @since 2019-11-29 10:24:10
 */
@Data
public class TbShip implements Serializable {
    private static final long serialVersionUID = 718239512610283320L;
    private Long shipId;
    private Integer shipTypeId;
    private String shipNumber;
    private String shipDepartment;
    private String comments;
    private Date createTime;
    private Date updateTime;


}