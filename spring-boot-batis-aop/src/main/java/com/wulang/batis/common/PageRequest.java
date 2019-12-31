package com.wulang.batis.common;

import lombok.Data;

@Data
public class PageRequest<T>  {

	private Integer page;
	private Integer limit;
	private T searchCondition;

}
