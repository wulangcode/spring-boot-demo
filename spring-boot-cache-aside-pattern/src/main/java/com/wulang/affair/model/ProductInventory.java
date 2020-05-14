package com.wulang.affair.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存数量
 *
 * @author wulang
 * @create 2020/5/13/20:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventory {
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 库存数量
     */
    private Long inventoryCnt;
}
