package com.wulang.affair.service.impl;

import com.wulang.affair.mapper.ProductInventoryMapper;
import com.wulang.affair.model.ProductInventory;
import com.wulang.affair.service.ProductInventoryService;
import com.wulang.affair.utils.RedisClusterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品库存Service实现类
 *
 * @author wulang
 * @create 2020/5/13/20:38
 */
@Service("productInventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInventoryServiceImpl.class);

    static String INVENTORY = "product:inventory:";

    @Resource
    private ProductInventoryMapper productInventoryMapper;

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
        LOGGER.info("已修改数据库中的库存，商品id=" + productInventory.getProductId() + ", 商品库存数量=" + productInventory.getInventoryCnt());
    }

    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        String key = INVENTORY + productInventory.getProductId();
        RedisClusterUtils.delString(key);
        LOGGER.info("已删除redis中的缓存，key=" + key);
    }

    /**
     * 根据商品id查询商品库存
     *
     * @param productId 商品id
     * @return 商品库存
     */
    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.findProductInventory(productId);
    }

    /**
     * 设置商品库存的缓存
     *
     * @param productInventory 商品库存
     */
    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        String key = INVENTORY + productInventory.getProductId();
        String s = RedisClusterUtils.setString(key, String.valueOf(productInventory.getInventoryCnt()));
        System.out.println(s);
        LOGGER.info("已更新商品库存的缓存，商品id=" + productInventory.getProductId() + ", 商品库存数量=" + productInventory.getInventoryCnt() + ", key=" + key);
    }

    /**
     * 获取商品库存的缓存
     *
     * @param productId
     * @return
     */
    @Override
    public ProductInventory getProductInventoryCache(Integer productId) {
        Long inventoryCnt = 0L;

        String key = INVENTORY + productId;
        String result = RedisClusterUtils.getString(key);
        if (result != null && !"".equals(result)) {
            try {
                inventoryCnt = Long.valueOf(result);
                return new ProductInventory(productId, inventoryCnt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
