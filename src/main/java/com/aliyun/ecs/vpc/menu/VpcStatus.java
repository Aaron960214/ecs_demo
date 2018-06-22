package com.aliyun.ecs.vpc.menu;

public enum VpcStatus {

    Pending("配置中"),
    Available("可用");

    private String description;


    VpcStatus() {
    }

    VpcStatus(String description) {
        this.description = description;
    }
}
