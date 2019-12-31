package com.wulang.security.admin.entity.collection;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Oilparas)实体类
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
@Data
public class Oilparas implements Serializable {
    private static final long serialVersionUID = 779621483872614188L;
    
    private String watertimedate;
    
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
    
    private Date waterinserttime;
    
    private Double grantimedate;
    
    private Double iso4um;
    
    private Double iso6um;
    
    private Double iso14um;
    
    private Double iso21um;
    
    private Double sae4um;
    
    private Double sae6um;
    
    private Double sae14um;
    
    private Double sae21um;
    
    private Double nas;
    
    private Double gost;
    
    private Double conc4um;
    
    private Double conc6um;
    
    private Double conc14um;
    
    private Double conc21um;
    
    private Double findex;
    
    private Double mtime;
    
    private Date graninserttime;
    
    private Double viscosity;
    
    private Double density;
    
    private Double dielectricconstant;
    
    private Double temperature;
    
    private Double waterppm;
    
    private Double dynamicviscosit;
    
    private Date viscosityinserttime;
    
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
    
    private String datasource;

}