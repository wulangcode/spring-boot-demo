package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class TbShip implements Serializable {
    private static final long serialVersionUID = -11300324356243286L;
    //船舶id
    private Long shipId;
    //船舶类型id
    private Integer shipTypeId;

    @TableField(exist = false)
    private String shipTypeName;
    //船舶编号
    private String shipNumber;
    //所属单位
    private String shipDepartment;
    //备注
    private String comments;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

}