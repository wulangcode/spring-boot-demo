package com.wulang.affair.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求的响应
 *
 * @author wulang
 * @create 2020/5/13/21:16
 */
@Data
@NoArgsConstructor
public class Response {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private String status;
    private String message;



    public Response(String status) {
        this.status = status;
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

}
