package com.wulang.quartz.enums;

public enum JobStatus {

    RUNNING("RUNNING"),
    COMPLETE("COMPLETE"),
    PAUSED("PAUSED");

    // 响应代码
    private String status;

    JobStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
