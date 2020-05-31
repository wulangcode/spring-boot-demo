package com.wulang.affair.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.wulang.affair.model.ProductInfo;
import com.wulang.affair.utils.HttpClientUtils;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author wulang
 * @create 2020/5/23/10:52
 */
public class GetProductInfosCommand extends HystrixObservableCommand<ProductInfo> {

    private String[] productIds;

    public GetProductInfosCommand(String[] productIds) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
        this.productIds = productIds;
    }

    @Override
    protected Observable<ProductInfo> construct() {
        return Observable.create(new Observable.OnSubscribe<ProductInfo>() {

            public void call(Subscriber<? super ProductInfo> observer) {
                try {
                    for(String productId : productIds) {
                        String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
                        String response = HttpClientUtils.sendGetRequest(url);
                        ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);
                        observer.onNext(productInfo);
                    }
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }

        }).subscribeOn(Schedulers.io());
    }

}
