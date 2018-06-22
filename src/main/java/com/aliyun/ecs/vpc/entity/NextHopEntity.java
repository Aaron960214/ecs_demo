package com.aliyun.ecs.vpc.entity;

import com.aliyun.ecs.vpc.menu.NextHopType;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: </p>
 * <p>Description: 下一跳 实体类型 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public class NextHopEntity {

    private Long id;

    private String name;

    /**
     * <p>Description: 下一跳的类型 默认Instance（ECS实例）</p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private NextHopType nextHopType;

    /**
     * <p>Description: 下一跳实例的id </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private String nextHopId;

    /**
     * <p>Description: 是否启用下一跳 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private Integer enabled;

    /**
     * <p>Description: 下一跳的路由权重 </p>
     * <p>Author:qbLiu/刘琪斌</p>
     */
    private Integer weight;



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

    public NextHopType getNextHopType() {
        return nextHopType;
    }

    public void setNextHopType(NextHopType nextHopType) {
        this.nextHopType = nextHopType;
    }

    public String getNextHopId() {
        return nextHopId;
    }

    public void setNextHopId(String nextHopId) {
        this.nextHopId = nextHopId;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
