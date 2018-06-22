package com.aliyun.ecs.vpc.menu;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: 路由条目的类型 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public enum RouteEntryType {

    System("系统路由"),
    Custom("自定义路由"),
    BGP("BGP路由");

    private String description;

    RouteEntryType() {

    }

    RouteEntryType(String description) {
        this.description = description;
    }



}
