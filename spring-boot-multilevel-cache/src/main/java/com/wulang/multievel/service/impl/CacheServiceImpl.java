package com.wulang.multievel.service.impl;

import com.wulang.multievel.hystrix.command.GetProductInfoFromReidsCacheCommand;
import com.wulang.multievel.hystrix.command.GetShopInfoFromReidsCacheCommand;
import com.wulang.multievel.hystrix.command.SaveProductInfo2ReidsCacheCommand;
import com.wulang.multievel.hystrix.command.SaveShopInfo2ReidsCacheCommand;
import com.wulang.multievel.model.ProductInfo;
import com.wulang.multievel.model.ShopInfo;
import com.wulang.multievel.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存Service实现类
 * @author Administrator
 *
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

	public static final String CACHE_NAME = "local";

	/**
	 * 将商品信息保存到本地缓存中
	 * @param productInfo
	 * @return
	 */
	@Override
	@CachePut(value = CACHE_NAME, key = "'key_'+#productInfo.getId()")
	public ProductInfo saveLocalCache(ProductInfo productInfo) {
		return productInfo;
	}

	/**
	 * 从本地缓存中获取商品信息
	 * @param id
	 * @return
	 */
	@Override
	@Cacheable(value = CACHE_NAME, key = "'key_'+#id")
	public ProductInfo getLocalCache(Long id) {
		return null;
	}

	/**
	 * 将商品信息保存到本地的ehcache缓存中
	 * @param productInfo
	 */
	@Override
	@CachePut(value = CACHE_NAME, key = "'product_info_'+#productInfo.getId()")
	public ProductInfo saveProductInfo2LocalCache(ProductInfo productInfo) {
		return productInfo;
	}

	/**
	 * 从本地ehcache缓存中获取商品信息
	 * @param productId
	 * @return
	 */
	@Override
	@Cacheable(value = CACHE_NAME, key = "'product_info_'+#productId")
	public ProductInfo getProductInfoFromLocalCache(Long productId) {
		return null;
	}

	/**
	 * 将店铺信息保存到本地的ehcache缓存中
	 * @param
	 */
	@Override
	@CachePut(value = CACHE_NAME, key = "'shop_info_'+#shopInfo.getId()")
	public ShopInfo saveShopInfo2LocalCache(ShopInfo shopInfo) {
		return shopInfo;
	}

	/**
	 * 从本地ehcache缓存中获取店铺信息
	 * @param
	 * @return
	 */
	@Override
	@Cacheable(value = CACHE_NAME, key = "'shop_info_'+#shopId")
	public ShopInfo getShopInfoFromLocalCache(Long shopId) {
		return null;
	}

	/**
	 * 将商品信息保存到redis中
	 * @param productInfo
	 */
	@Override
	public void saveProductInfo2ReidsCache(ProductInfo productInfo) {
//		String key = "product_info_" + productInfo.getId();
//        RedisClusterUtils.setString(key,JSONObject.toJSONString(productInfo));
        SaveProductInfo2ReidsCacheCommand saveProductInfo2ReidsCacheCommand =
            new SaveProductInfo2ReidsCacheCommand(productInfo);
        saveProductInfo2ReidsCacheCommand.execute();
	}

	/**
	 * 将店铺信息保存到redis中
	 * @param
	 */
	@Override
	public void saveShopInfo2ReidsCache(ShopInfo shopInfo) {
//		String key = "shop_info_" + shopInfo.getId();
//		RedisClusterUtils.setString(key, JSONObject.toJSONString(shopInfo));
        SaveShopInfo2ReidsCacheCommand saveShopInfo2ReidsCacheCommand = new SaveShopInfo2ReidsCacheCommand(shopInfo);
        saveShopInfo2ReidsCacheCommand.execute();
    }

	/**
	 * 从redis中获取商品信息
	 * @param
	 */
	@Override
	public ProductInfo getProductInfoFromReidsCache(Long productId) {
//		String key = "product_info_" + productId;
//        String json = RedisClusterUtils.getString(key);
//
//		return JSONObject.parseObject(json, ProductInfo.class);
        GetProductInfoFromReidsCacheCommand command = new GetProductInfoFromReidsCacheCommand(productId);
        return command.execute();
	}

	/**
	 * 从redis中获取店铺信息
	 * @param
	 */
	@Override
	public ShopInfo getShopInfoFromReidsCache(Long shopId) {
//		String key = "shop_info_" + shopId;
//        String json = RedisClusterUtils.getString(key);
//		return JSONObject.parseObject(json, ShopInfo.class);
        GetShopInfoFromReidsCacheCommand command = new GetShopInfoFromReidsCacheCommand(shopId);
        return command.execute();
	}

}
