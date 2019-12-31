package com.wulang.batis.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageResponse<T> {
    private Long page;

    private Long limit;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 数据集
     */
    private T resultData;

    public PageResponse(Long page, Long limit, Long total, Long totalPage, T resultData) {
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.totalPage = totalPage;
        this.resultData = resultData;
    }

}
