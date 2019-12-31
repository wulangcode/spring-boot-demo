package com.wulang.security.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageResponse<T> {
    private Integer page;

    private Integer limit;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 数据集
     */
    private T resultData;

    public PageResponse(Integer page, Integer limit, Long total, Integer totalPage, T resultData) {
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.totalPage = totalPage;
        this.resultData = resultData;
    }

}
