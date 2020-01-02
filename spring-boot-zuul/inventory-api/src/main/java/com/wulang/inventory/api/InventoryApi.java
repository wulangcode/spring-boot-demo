package com.wulang.inventory.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/inventory")
public interface InventoryApi {

	@RequestMapping(value = "/deduct/{productId}/{stock}", method = RequestMethod.PUT)
	String deductStock(
        @PathVariable("productId") Long productId,
        @PathVariable("stock") Long stock);
	
}
