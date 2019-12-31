package com.wulang.security.admin.entity.monitor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 船舶发动机数据(TbCShipDeviceData)实体类
 *
 * @author makejava
 * @since 2019-11-29 17:50:54
 */
@Data
@TableName("tb_ship_device_data")
public class MonitorDeviceData implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 船舶id
     */
    private Long shipId;

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 采集时间
     */
    private Date gatherTime;

    /**
     * 润滑油的温度
     */
    private Double t;

    /**
     * 介电常数
     */
    private Double p;

    /**
     * 40度下的介电常数
     */
    private Double p40;

    /**
     * 电导率
     */
    private Double c;

    /**
     * 40度下电导率
     */
    private Double c40;

    /**
     * 相对湿度值
     */
    private Double rh;

    /**
     * 20度相对湿度值
     */
    private Double rh20;

    /**
     * 润滑油的平均温度
     */
    @TableField("TMean")
    private Double TMean;

    private Double pcbt;

    private Double rult;

    private Double rullg;

    private Double rul;

    private Double app40;

    private Double apc40;

    private Double ap;

    private Double fb;

    @TableField("OAge")
    private Double OAge;

    private String erc;

    private String crc;

    /**
     * 大于4um颗粒的浓度的污染度等级，ISO 标准
     */
    @TableField("ISO4um")
    private Double ISO4um;

    /**
     * 大于6um颗粒的浓度的污染度等级，ISO 标准
     */
    @TableField("ISO6um")
    private Double ISO6um;

    /**
     * 大于14um颗粒的浓度的污染度等级，ISO 标准
     */
    @TableField("ISO14um")
    private Double ISO14um;

    /**
     * 大于21um颗粒的浓度的污染度等级，ISO 标准
     */
    @TableField("ISO21um")
    private Double ISO21um;

    /**
     * 大于4um颗粒的浓度的污染度等级，SAE 标准
     */
    @TableField("SAE4um")
    private Double SAE4um;

    /**
     * 大于6um颗粒的浓度的污染度等级，SAE 标准
     */
    @TableField("SAE6um")
    private Double SAE6um;

    /**
     * 大于14um颗粒的浓度的污染度等级，SAE 标准
     */
    @TableField("SAE14um")
    private Double SAE14um;

    /**
     * 大于21um颗粒的浓度的污染度等级，SAE 标准
     */
    @TableField("SAE21um")
    private Double SAE21um;

    /**
     * 表示油液洁净度等级，NAS 等级
     */
    private Double nas;

    /**
     * GOST
     */
    private Double gost;

    /**
     * 大于4微米颗粒数目
     */
    @TableField("Conc4um")
    private Double Conc4um;

    /**
     * 大于6微米颗粒数目
     */
    @TableField("Conc6um")
    private Double Conc6um;

    /**
     * 大于14微米颗粒数目
     */
    @TableField("Conc14um")
    private Double Conc14um;

    /**
     * 大于21微米颗粒数目
     */
    @TableField("Conc21um")
    private Double Conc21um;

    /**
     * f指数
     */
    @TableField("FIndex")
    private Double FIndex;

    /**
     * 平均时间
     */
    @TableField("MTime")
    private Double MTime;

    /**
     * 润滑油的动力粘度
     */
    @TableField("Viscosity")
    private Double Viscosity;

    /**
     * 润滑油的密度
     */
    @TableField("Density")
    private Double Density;

    /**
     * 润滑油的介电常数
     */
    @TableField("DielectricConstant")
    private Double DielectricConstant;

    /**
     * 润滑油的温度
     */
    @TableField("Temperature")
    private Double Temperature;

    @TableField("Waterppm")
    private Double Waterppm;

    /**
     * 润滑油的运动粘度
     */
    @TableField("DynamicViscosity")
    private Double DynamicViscosity;

    private Integer concurrent;

}