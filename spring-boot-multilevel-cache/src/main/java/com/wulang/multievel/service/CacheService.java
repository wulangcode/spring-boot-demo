package com.wulang.multievel.service;

import com.wulang.multievel.model.ProductInfo;
import com.wulang.multievel.model.ShopInfo;

/**
 * 缓存service接口
 * @author Administrator
 *
 */
public interface CacheService {

	/**
	 * 将商品信息保存到本地缓存中
	 * @param productInfo
	 * @return
	 */
	public ProductInfo saveLocalCache(ProductInfo productInfo);

	/**
	 * 从本地缓存中获取商品信息
	 * @param id
	 * @return
	 */
	public ProductInfo getLocalCache(Long id);

	/**
	 * 将商品信息保存到本地的ehcache缓存中
	 * @param productInfo
	 */
	public ProductInfo saveProductInfo2LocalCache(ProductInfo productInfo);

	/**
	 * 从本地ehcache缓存中获取商品信息
	 * @param productId
	 * @return
	 */
	public ProductInfo getProductInfoFromLocalCache(Long productId);

	/**
	 * 将店铺信息保存到本地的ehcache缓存中
	 * @param
	 */
	public ShopInfo saveShopInfo2LocalCache(ShopInfo shopInfo);

	/**
	 * 从本地ehcache缓存中获取店铺信息
	 * @param
	 * @return
	 */
	public ShopInfo getShopInfoFromLocalCache(Long shopId);

	/**
	 * 将商品信息保存到redis中
	 * @param productInfo
	 */
	public void saveProductInfo2ReidsCache(ProductInfo productInfo);

	/**
	 * 将店铺信息保存到redis中
	 * @param
	 */
	public void saveShopInfo2ReidsCache(ShopInfo shopInfo);

	/**
	 * 从redis中获取商品信息
	 * @param
	 */
	public ProductInfo getProductInfoFromReidsCache(Long productId);

	/**
	 * 从redis中获取店铺信息
	 * @param
	 */
	public ShopInfo getShopInfoFromReidsCache(Long shopId);

}
