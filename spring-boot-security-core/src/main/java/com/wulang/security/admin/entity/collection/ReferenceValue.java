package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (ReferenceValue)实体类
 *
 * @author makejava
 * @since 2019-11-22 10:18:42
 */
@Data
@TableName("reference_value")
@ApiModel(value = "ReferenceValue", description = "参考值对象")
public class ReferenceValue implements Serializable {
    private static final long serialVersionUID = -42964916041695428L;
    /**
     * 参考值id
     */
    @ApiModelProperty(value = "参考值id")
    private Integer id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String title;
    /**
     * 参考值
     */
    @ApiModelProperty(value = "参考值")
    private Double value;
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}