package com.aliyun.ecs.vpc.menu;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: 弹性公网IP（EIP）的状态： </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public enum EIPStatus {

    Associating("绑定中"),
    Unassociating("解绑中"),
    InUse("已分配"),
    vailable("可用");


    private String decsription;

    EIPStatus() {
    }

    EIPStatus(String decsription) {

        this.decsription = decsription;
    }
}
