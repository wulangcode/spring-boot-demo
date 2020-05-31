package com.wulang.multievel.rebuild;

import com.wulang.multievel.model.ProductInfo;
import com.wulang.multievel.service.CacheService;
import com.wulang.multievel.spring.SpringContext;
import com.wulang.multievel.zk.ZooKeeperSession;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 缓存重建线程
 *
 * @author wulang
 * @create 2020/5/16/17:43
 */
public class RebuildCacheThread implements Runnable {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void run() {
        RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
        ZooKeeperSession zkSession = ZooKeeperSession.getInstance();
        CacheService cacheService = (CacheService) SpringContext.getApplicationContext()
            .getBean("cacheService");

        while (true) {
            ProductInfo productInfo = rebuildCacheQueue.takeProductInfo();

            zkSession.acquireDistributedLock(productInfo.getId());

            ProductInfo existedProductInfo = cacheService.getProductInfoFromReidsCache(productInfo.getId());

            if (existedProductInfo != null) {
                // 比较当前数据的时间版本比已有数据的时间版本是新还是旧
                try {
                    Date date = sdf.parse(productInfo.getModifiedTime());
                    Date existedDate = sdf.parse(existedProductInfo.getModifiedTime());

                    if (date.before(existedDate)) {
                        System.out.println("current date[" + productInfo.getModifiedTime() + "] is before existed date[" + existedProductInfo.getModifiedTime() + "]");
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("current date[" + productInfo.getModifiedTime() + "] is after existed date[" + existedProductInfo.getModifiedTime() + "]");
            } else {
                System.out.println("existed product info is null......");
            }

            cacheService.saveProductInfo2LocalCache(productInfo);
            cacheService.saveProductInfo2ReidsCache(productInfo);

            zkSession.releaseDistributedLock(productInfo.getId());
        }
    }

}

