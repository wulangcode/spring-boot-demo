package com.wulang.inventory.service;

import com.wulang.inventory.api.InventoryApi;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryService implements InventoryApi {

	public String deductStock(@PathVariable("productId") Long productId,
			@PathVariable("stock") Long stock) {
		System.out.println("对商品【productId=" + productId + "】扣减库存：" + stock);    
		return "{'msg': 'success'}";
	}

}
