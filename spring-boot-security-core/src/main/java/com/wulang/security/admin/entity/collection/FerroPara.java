package com.wulang.security.admin.entity.collection;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (FerroPara)实体类
 *
 * @author makejava
 * @since 2019-11-19 10:43:18
 */
@Data
public class FerroPara implements Serializable {
    private static final long serialVersionUID = -80038117612376643L;
    
    private String ferroid;
    
    private Double ferroval;
    
    private String ferrotime;
    
    private String oilid;
    
    private Double oilval;
    
    private Double oildeg;
    
    private String moistureid;
    
    private Double moisturequality;
    
    private Double moisturedeg;
    
    private Date ferroinserttime;



}