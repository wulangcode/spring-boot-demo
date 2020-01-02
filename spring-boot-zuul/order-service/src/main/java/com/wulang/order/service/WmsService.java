package com.wulang.order.service;

import com.wulang.wms.api.WmsApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "wms-service")
public interface WmsService extends WmsApi {

}
