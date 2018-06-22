package com.aliyun.ecs.vpc.entity;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public class VSwitchEntity {

    private Long id;

    /**
     * <p>Description: 交换机的名称</p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String vSwitchName;

    /**
     * <p>Description:交换机的ID </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String vSwitchId;

    /**
     * <p>Description:交换机所属VPC的ID </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String vpcId;

    /**
     * <p>Description: 	交换机的状态  Pending  Available </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    //TODO 枚举
    private String status;

    /**
     * <p>Description: 交换机的网段 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String CidrBlock;

    /**
     * <p>Description: 交换机所在的可用区 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String zoneId;

    /**
     * <p>Description:交换机中可用的IP地址数量 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private Integer availableIpAddressCount;


    private String description;

    /**
     * <p>Description:交换机的创建时间 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String creationTime;

    /**
     * <p>Description:是否是该可用区的默认交换机 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private Boolean isDefault;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getvSwitchName() {
        return vSwitchName;
    }

    public void setvSwitchName(String vSwitchName) {
        this.vSwitchName = vSwitchName;
    }

    public String getvSwitchId() {
        return vSwitchId;
    }

    public void setvSwitchId(String vSwitchId) {
        this.vSwitchId = vSwitchId;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCidrBlock() {
        return CidrBlock;
    }

    public void setCidrBlock(String cidrBlock) {
        CidrBlock = cidrBlock;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getAvailableIpAddressCount() {
        return availableIpAddressCount;
    }

    public void setAvailableIpAddressCount(Integer availableIpAddressCount) {
        this.availableIpAddressCount = availableIpAddressCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
