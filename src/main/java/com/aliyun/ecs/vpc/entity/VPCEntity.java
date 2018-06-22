package com.aliyun.ecs.vpc.entity;

import com.aliyun.ecs.vpc.menu.VpcStatus;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company:</p>
 * <p>Description: VPC实体类 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public class VPCEntity {

    private Long id;

    private String name;

    private String regionId;

    //VPC的网段
    private String cidrBlock;

    private String description;

    private String clientToken;

    private String userCidr;

    private String vpcId;

    //如果为true，则查询所有，包括默认vpc
    private Boolean isDefault;

    //
    private VpcStatus status;

    //VPC中交换机的列表
    private String vSwitchIds;

    //VPC路由器的ID
    private String vRouterId;

    //VPC的创建时间  创建不需要设置，查询时会返回
    private String creationTime;


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

    public String getCidrBlock() {
        return cidrBlock;
    }

    public void setCidrBlock(String cidrBlock) {
        this.cidrBlock = cidrBlock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getUserCidr() {
        return userCidr;
    }

    public void setUserCidr(String userCidr) {
        this.userCidr = userCidr;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public VpcStatus getStatus() {
        return status;
    }

    public void setStatus(VpcStatus status) {
        this.status = status;
    }

    public String getvSwitchIds() {
        return vSwitchIds;
    }

    public void setvSwitchIds(String vSwitchIds) {
        this.vSwitchIds = vSwitchIds;
    }

    public String getvRouterId() {
        return vRouterId;
    }

    public void setvRouterId(String vRouterId) {
        this.vRouterId = vRouterId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
