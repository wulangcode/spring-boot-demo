package com.wulang.security.admin.entity.collection;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * (WaterMonitorPara)实体类
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
@Data
@Getter
@Setter
public class WaterMonitorPara implements Serializable {
    private static final long serialVersionUID = -34960158991929183L;
    
    private Date timeDate;
    
    private Double t;
    
    private Double p;
    
    private Double p40;
    
    private Double c;
    
    private Double c40;
    
    private Double rh;
    
    private Double rh20;
    
    private Double tmean;
    
    private Double pcbt;
    
    private Double rult;
    
    private Double rullg;
    
    private Double rul;
    
    private Double app40;
    
    private Double apc40;
    
    private Double ap;
    
    private Double fb;
    
    private Double oage;
    
    private Date insertTime;


}