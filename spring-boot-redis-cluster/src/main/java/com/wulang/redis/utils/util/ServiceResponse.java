package com.wulang.redis.utils.util;

import java.io.Serializable;

/**
 * Service 层返回结果
 * @author hzk
 * @date 2019/04/16
 */
public class ServiceResponse implements Serializable {
    private boolean success;
    private Object data;
    private String msg;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
