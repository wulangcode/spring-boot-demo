package com.wulang.spider.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wulang
 * @create 2020/5/2/9:21
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(int code, String msg) {
        this(code, msg, null);
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Errors errors) {
        StringBuilder msg = new StringBuilder();
        errors.getFieldErrors().forEach((ObjectError error) -> {
            msg.append(error.getDefaultMessage() + "\n");
        });

        this.code = 1;
        this.msg = msg.toString();

        if (msg.toString().indexOf("java.lang.IllegalStateException") > 0) {
            this.msg = "请检验参数是否合法";
        }
        if (msg.toString().indexOf("java.lang.NumberFormatException") > 0) {
            this.msg = "参数格式有误";
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void addMapData(String key, Object value) {
        if (data == null) {
            data = (T) new HashMap();
        }
        if (!(data instanceof Map)) {
            throw new IllegalArgumentException("已设置其他类型参数");
        }
        ((Map) data).put(key, value);
    }


}
