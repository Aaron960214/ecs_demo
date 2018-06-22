package com.aliyun.ecs.vpc.entity;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: 路由器 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public class RouteEntity {

    private Long id;

    private String name;

    private String regionId;

    //云平台上的路由id
    private String routerId;

    private String description;

    //路由器的路由表id
    private String routeTableIds;

    //所属vpc的id
    private String vpcId;

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

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRouteTableIds() {
        return routeTableIds;
    }

    public void setRouteTableIds(String routeTableIds) {
        this.routeTableIds = routeTableIds;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
