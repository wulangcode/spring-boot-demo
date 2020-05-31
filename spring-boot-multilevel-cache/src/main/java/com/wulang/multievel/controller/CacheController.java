package com.wulang.multievel.controller;

import com.wulang.multievel.hystrix.command.GetProductInfoCommand;
import com.wulang.multievel.model.ProductInfo;
import com.wulang.multievel.model.ShopInfo;
import com.wulang.multievel.rebuild.RebuildCacheQueue;
import com.wulang.multievel.service.CacheService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 缓存Controller
 *
 * @author wulang
 * @create 2020/5/16/14:52
 */
@RestController
@RequestMapping("/multievel")
public class CacheController {

    @Resource
    private CacheService cacheService;

    @RequestMapping("/testPutCache")
    @ResponseBody
    public String testPutCache(ProductInfo productInfo) {
        cacheService.saveLocalCache(productInfo);
        return "success";
    }

    @RequestMapping("/testGetCache")
    @ResponseBody
    public ProductInfo testGetCache(Long id) {
        return cacheService.getLocalCache(id);
    }

    @PostMapping("/testSetCache")
    @ResponseBody
    public ProductInfo testSetCache(@RequestBody ProductInfo productInfo) {
        return cacheService.saveProductInfo2LocalCache(productInfo);
    }

    @GetMapping("/getProductInfo/{id}")
    @ResponseBody
    public ProductInfo getProductInfo(@PathVariable("id") Long productId) {
        ProductInfo productInfo = null;

        productInfo = cacheService.getProductInfoFromReidsCache(productId);
        System.out.println("=================从redis中获取缓存，商品信息=" + productInfo);

        if (productInfo == null) {
            productInfo = cacheService.getProductInfoFromLocalCache(productId);
            System.out.println("=================从ehcache中获取缓存，商品信息=" + productInfo);
        }

        if (productInfo == null) {
            // 就需要从数据源重新拉去数据，重建缓存，但是这里先不讲
            /*String productInfoJSON = "{\"id\": 5, \"name\": \"iphone7手机\", \"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", \"specification\": \"iphone7的规格\", \"service\": \"iphone7的售后服务\", \"color\": \"红色,白色,黑色\", \"size\": \"5.5\", \"shopId\": 1, \"modifiedTime\": \"2017-01-01 12:01:00\"}";
            productInfo = JSONObject.parseObject(productInfoJSON, ProductInfo.class);*/
            //防止MySQL被打死，使用多重降级，Rejection
            GetProductInfoCommand command = new GetProductInfoCommand(productId);
            productInfo = command.execute();
            // 将数据推送到一个内存队列中
            RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
            rebuildCacheQueue.putProductInfo(productInfo);
        }

        return productInfo;
    }

    @GetMapping("/getShopInfo/{id}")
    @ResponseBody
    public ShopInfo getShopInfo(@PathVariable("id") Long shopId) {
        ShopInfo shopInfo = null;

        shopInfo = cacheService.getShopInfoFromReidsCache(shopId);
        System.out.println("=================从redis中获取缓存，店铺信息=" + shopInfo);

        if (shopInfo == null) {
            shopInfo = cacheService.getShopInfoFromLocalCache(shopId);
            System.out.println("=================从ehcache中获取缓存，店铺信息=" + shopInfo);
        }

        if (shopInfo == null) {
            // 就需要从数据源重新拉去数据，重建缓存，但是这里先不讲
        }

        return shopInfo;
    }

}

