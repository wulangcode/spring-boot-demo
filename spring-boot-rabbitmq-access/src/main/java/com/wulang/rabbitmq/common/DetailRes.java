package com.wulang.rabbitmq.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//统一返回值,可描述失败细节
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailRes {
    boolean isSuccess;
    String errMsg;
}
