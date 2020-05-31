package com.wulang.multievel.mapper;

import com.wulang.multievel.model.ProductInventory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 库存数量Mapper
 *
 * @author wulang
 * @create 2020/5/13/20:32
 */
@Repository
public interface ProductInventoryMapper {
    /**
     * 更新库存数量
     *
     * @param productInventory 商品库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 根据商品id查询商品库存信息
     *
     * @param productId 商品id
     * @return 商品库存信息
     */
    ProductInventory findProductInventory(@Param("productId") Integer productId);
}
