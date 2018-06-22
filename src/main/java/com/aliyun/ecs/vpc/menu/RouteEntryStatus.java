package com.aliyun.ecs.vpc.menu;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: 路由条目的状态 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public enum RouteEntryStatus {

    Pending("配置中"),
    Available("可用"),
    Modifying("修改中");


    private String descriprtion;


    RouteEntryStatus(String descriprtion) {
        this.descriprtion = descriprtion;
    }

    RouteEntryStatus() {
    }


}
