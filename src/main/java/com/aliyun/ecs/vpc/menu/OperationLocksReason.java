package com.aliyun.ecs.vpc.menu;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description:  弹性公网IP被锁定的原因  </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public enum OperationLocksReason {


    financial("因欠费被锁定"),
    security("因安全原因被锁定");


    private String description;


    OperationLocksReason() {
    }

    OperationLocksReason(String description) {
        this.description = description;
    }


}
