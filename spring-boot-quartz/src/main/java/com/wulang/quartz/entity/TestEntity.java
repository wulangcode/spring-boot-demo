package com.wulang.quartz.entity;

import lombok.Data;

@Data
public class TestEntity {
    private String name;
    private int age;

    public TestEntity(){}

    public TestEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
