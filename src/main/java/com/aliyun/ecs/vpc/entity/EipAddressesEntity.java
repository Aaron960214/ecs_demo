package com.aliyun.ecs.vpc.entity;

import com.aliyun.ecs.vpc.menu.AssociatedInstanceType;
import com.aliyun.ecs.vpc.menu.EIPStatus;
import com.aliyun.ecs.vpc.menu.OperationLocksReason;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: 弹性公网ip </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public class EipAddressesEntity {

    private Long id;

    private String name;

    private String regionId;

    /**
     * <p>Description:EIP的地址 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String ipAddress;

    /**
     * <p>Description:EIP的ID </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String allocationId;

    /**
     * <p>Description:EIP的状态 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private EIPStatus status;

    /**
     * <p>Description: 当前绑定的实例类型 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String instanceType;

    private String instanceId;

    //EIP的带宽峰值 单位Mbps
    private String Bandwidth;

    //EIP的计费方式
    private String internetChargeType;

    //EIP的创建时间
    private String allocationTime;

    //被锁定的原因
    private OperationLocksReason operationLocks;

    //要绑定的云产品实例的类型
    private AssociatedInstanceType associatedInstanceType;

    //云产品的实例ID
    private String associatedInstanceId;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public EIPStatus getStatus() {
        return status;
    }

    public void setStatus(EIPStatus status) {
        this.status = status;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getBandwidth() {
        return Bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        Bandwidth = bandwidth;
    }

    public String getInternetChargeType() {
        return internetChargeType;
    }

    public void setInternetChargeType(String internetChargeType) {
        this.internetChargeType = internetChargeType;
    }

    public String getAllocationTime() {
        return allocationTime;
    }

    public void setAllocationTime(String allocationTime) {
        this.allocationTime = allocationTime;
    }

    public OperationLocksReason getOperationLocks() {
        return operationLocks;
    }

    public void setOperationLocks(OperationLocksReason operationLocks) {
        this.operationLocks = operationLocks;
    }

    public AssociatedInstanceType getAssociatedInstanceType() {
        return associatedInstanceType;
    }

    public void setAssociatedInstanceType(AssociatedInstanceType associatedInstanceType) {
        this.associatedInstanceType = associatedInstanceType;
    }

    public String getAssociatedInstanceId() {
        return associatedInstanceId;
    }

    public void setAssociatedInstanceId(String associatedInstanceId) {
        this.associatedInstanceId = associatedInstanceId;
    }
}
