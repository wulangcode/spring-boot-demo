package com.wulang.security.admin.entity.collection;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author tangxi
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbShipDevice implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 设备ID
     */
    @TableId(value = "device_id", type = IdType.AUTO)
    private Integer deviceId;

    /**
     * 船舶ID
     */
    private Long shipId;

    private String deviceName;

    /**
     * 采集的设备类型：
     */
    private Integer deviceTypeId;

    private String serialPort;

    private Date createTime;

    private Date updateTime;

}
