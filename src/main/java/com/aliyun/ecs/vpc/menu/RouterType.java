package com.aliyun.ecs.vpc.menu;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: 路由器的类型 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public enum RouterType {

    VRouter("VPC路由器"),

    VBR("边界路由器");

    private String description;


    RouterType() {
    }

    RouterType(String description) {
        this.description = description;
    }
}
