package com.aliyun.ecs.vpc.entity;

import java.util.List;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: 路由条目 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public class RouteEntryEntity {

    private Long id;
    private String name;

    /**
     * <p>Description: 路由条目的类型  </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    //TODO 枚举
    private String type;

    /**
     * <p>Description: 路由条目所属路由表的ID </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String routeTableId;

    /**
     * <p>Description: 路由条目的目标网段 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String destinationCidrBlock;

    /**
     * <p>Description:下一跳的类型 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    //TODO 枚举
    private String nextHopType;

    /**
     * <p>Description: 下一跳的实例ID </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String instanceId;

    /**
     * <p>Description: 路由条目的状态 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    //TODO 枚举
    private String status;

    /**
     * <p>Description:路由条目所在路由器的ID </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String routerId;

    /**
     * <p>Description: ECMP路由的下一跳列表 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private List<NextHopEntity> nextHops;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRouteTableId() {
        return routeTableId;
    }

    public void setRouteTableId(String routeTableId) {
        this.routeTableId = routeTableId;
    }

    public String getDestinationCidrBlock() {
        return destinationCidrBlock;
    }

    public void setDestinationCidrBlock(String destinationCidrBlock) {
        this.destinationCidrBlock = destinationCidrBlock;
    }

    public String getNextHopType() {
        return nextHopType;
    }

    public void setNextHopType(String nextHopType) {
        this.nextHopType = nextHopType;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public List<NextHopEntity> getNextHops() {
        return nextHops;
    }

    public void setNextHops(List<NextHopEntity> nextHops) {
        this.nextHops = nextHops;
    }
}

