package com.aliyun.ecs.vpc.menu;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: 弹性公网 要绑定的云产品实例的类型 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public enum AssociatedInstanceType {

    //默认值
    EcsInstance("VPC类型的ECS实例"),

    SlbInstance("VPC类型的SLB实例"),

    Nat("NAT网关"),

    HaVip("HAVIP");


    private String description;


    AssociatedInstanceType(String description) {
        this.description = description;
    }

    AssociatedInstanceType() {

    }
}
