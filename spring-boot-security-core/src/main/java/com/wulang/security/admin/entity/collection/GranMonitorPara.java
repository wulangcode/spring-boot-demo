package com.wulang.security.admin.entity.collection;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (GranMonitorPara)实体类
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
@Data
public class GranMonitorPara implements Serializable {
    private static final long serialVersionUID = 674163066467801324L;
    
    private Date timeDate;
    
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
    
    private Date insertTime;


}