package com.wulang.security.admin.entity.monitor;

import lombok.Data;

import java.io.Serializable;

/**
 * (TbTitleIcon)实体类
 *
 * @author makejava
 * @since 2019-11-29 13:55:59
 */
@Data
public class TbTitleIcon implements Serializable {
    private static final long serialVersionUID = 727855450567122380L;
    
    private Integer id;
    
    private String title;

    private Double value;
    
    private String icon;
    
    private String color;

}