package com.wulang.order.service;

import com.wulang.credit.api.CreditApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "credit-service")
public interface CreditService extends CreditApi {

}
