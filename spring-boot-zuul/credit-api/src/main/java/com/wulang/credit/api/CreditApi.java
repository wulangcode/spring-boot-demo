package com.wulang.credit.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/credit")
public interface CreditApi {

	@RequestMapping(value = "/add/{userId}/{credit}", method = RequestMethod.PUT)
	String add(
        @PathVariable("userId") Long userId,
        @PathVariable("credit") Long credit);
	
}
