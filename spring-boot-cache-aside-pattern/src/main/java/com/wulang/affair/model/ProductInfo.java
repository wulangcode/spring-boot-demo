package com.wulang.affair.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**商品信息
 * @author wulang
 * @create 2020/5/23/10:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {

    private Long id;
    private String name;
    private Double price;
    private String pictureList;
    private String specification;
    private String service;
    private String color;
    private String size;
    private Long shopId;
    private String modifiedTime;
    private Long cityId;
    private String cityName;
    private Long brandId;
    private String brandName;

    @Override
    public String toString() {
        return "ProductInfo [id=" + id + ", name=" + name + ", price=" + price
            + ", pictureList=" + pictureList + ", specification="
            + specification + ", service=" + service + ", color=" + color
            + ", size=" + size + ", shopId=" + shopId + ", modifiedTime="
            + modifiedTime + ", cityId=" + cityId + ", cityName="
            + cityName + ", brandId=" + brandId + ", brandName="
            + brandName + "]";
    }

}
