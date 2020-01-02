package com.wulang.order.service;

import com.wulang.inventory.api.InventoryApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "inventory-service")
public interface InventoryService extends InventoryApi {

}
