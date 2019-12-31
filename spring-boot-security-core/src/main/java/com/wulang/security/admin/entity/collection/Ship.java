package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 船舶(TbShip)实体类
 *
 * @author makejava
 * @since 2019-11-29 13:32:59
 */
@Data
@TableName("tb_ship")
public class Ship implements Serializable {
    private static final long serialVersionUID = -11300324356243286L;
    private Long shipId;
    private Integer shipTypeId;

    @TableField(exist = false)
    private String shipTypeName;
    private String shipNumber;
    private String shipDepartment;
    private String comments;
    private Date createTime;
    private Date updateTime;

}