package com.wulang.security.admin.entity.monitor;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 船舶类型(TbShipType)实体类
 *
 * @author makejava
 * @since 2019-11-29 10:24:10
 */
@Data
public class TbShipType implements Serializable {
    private static final long serialVersionUID = -23072818009692633L;
    //船舶类型id
    private Integer shipTypeId;
    //船舶类型名称
    private String shipTypeName;
    //备注
    private String comments;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;


}