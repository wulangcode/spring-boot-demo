package com.wulang.affair.request;

import com.wulang.affair.model.ProductInventory;
import com.wulang.affair.service.ProductInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 重新加载商品的库存到缓存
 *
 * @author wulang
 * @create 2020/5/13/20:49
 */
public class ProductInventoryCacheRefreshRequest implements Request {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInventoryCacheRefreshRequest.class);

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品库存Service
     */
    private ProductInventoryService productInventoryService;

    /**
     * 是否强制刷新缓存
     */
    private boolean forceRefresh;

    public ProductInventoryCacheRefreshRequest(Integer productId,
                                               ProductInventoryService productInventoryService,
                                               boolean forceRefresh) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
        this.forceRefresh = forceRefresh;
    }

    @Override
    public void process() {
        LOGGER.info("===========日志===========: 从数据库中查询最新的商品库存数量，商品id=" + productId);
        // 从数据库中查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        LOGGER.info("===========日志===========: 将最新的商品库存数量，刷新到redis缓存中去，商品id=" + productId);
        // 将最新的商品库存数量，刷新到redis缓存中去
        productInventoryService.setProductInventoryCache(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }

    @Override
    public boolean isForceRefresh() {
        return forceRefresh;
    }
}
