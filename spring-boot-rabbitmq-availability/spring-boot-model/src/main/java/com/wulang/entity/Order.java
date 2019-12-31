package com.wulang.entity;

import java.io.Serializable;

/**
 * 业务实体类
 */
public class Order implements Serializable {
    private int id;
    private String name;
    private String messageId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}
