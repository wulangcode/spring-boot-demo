package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 发动机类型
 * </p>
 *
 * @author tangxi
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbDeviceType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发动机类型id
     */
    @TableId(value = "device_type_id", type = IdType.AUTO)
    private Integer deviceTypeId;

    private String deviceType;
    private Long deviceId;

    /**
     * 发动机类型名称
     */
    private String deviceTypeName;

    /**
     * 备注
     */
    private String comments;

    /**
     * 属性列表
     */
    private String attributes;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
