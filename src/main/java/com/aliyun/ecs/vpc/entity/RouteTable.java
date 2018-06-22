package com.aliyun.ecs.vpc.entity;

import java.util.List;

public class RouteTable {

    private Long id ;

    private String name;

    /**
     * <p>Description: 路由表所在路由器的类型 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    //TODO 枚举
    private String RouterType;

    /**
     * <p>Description: 路由表所在路由器的ID。 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String routerId;

    /**
     * <p>Description: VPC路由器的ID </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String vRouterId;

    /**
     * <p>Description: 路由表的ID </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String routeTableId;

    /**
     * <p>Description: 路由表的类型 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String routeTableType;

    /**
     * <p>Description:路由表的创建时间 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String creationTime;

    /**
     * <p>Description: 路由条目的详细信息 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private List<RouteEntryEntity> routeEntrys;



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

    public String getRouterType() {
        return RouterType;
    }

    public void setRouterType(String routerType) {
        RouterType = routerType;
    }

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public String getvRouterId() {
        return vRouterId;
    }

    public void setvRouterId(String vRouterId) {
        this.vRouterId = vRouterId;
    }

    public String getRouteTableId() {
        return routeTableId;
    }

    public void setRouteTableId(String routeTableId) {
        this.routeTableId = routeTableId;
    }

    public String getRouteTableType() {
        return routeTableType;
    }

    public void setRouteTableType(String routeTableType) {
        this.routeTableType = routeTableType;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public List<RouteEntryEntity> getRouteEntrys() {
        return routeEntrys;
    }

    public void setRouteEntrys(List<RouteEntryEntity> routeEntrys) {
        this.routeEntrys = routeEntrys;
    }
}
