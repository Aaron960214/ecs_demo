package com.aliyun.ecs.vpc.menu;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: 交换机的状态 </p>
 * <p>Description: </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public enum VSwitchStatus {

    Pending("配置中"),
    Available("可用");


    private String description;

    VSwitchStatus(String description) {
        this.description = description;
    }

    VSwitchStatus() {
    }
}
