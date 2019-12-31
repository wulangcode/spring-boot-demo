package com.wulang.security.admin.entity.collection;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (ViscosityMonitorPara)实体类
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
@Data
public class ViscosityMonitorPara implements Serializable {
    private static final long serialVersionUID = 781798387952302201L;
    
    private Double viscosity;
    
    private Double density;
    
    private Double dielectricConstant;
    
    private Double temperature;
    
    private Double dynamicViscosity;
    
    private Double waterppm;
    
    private Date insertTime;

    private Double oilppm;
}