package com.aliyun.ecs.vpc.entity;

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
    private String status;

    //VPC中交换机的列表
    private String vSwitchIds;

    //VPC路由器的ID
    private String vRouterId;

    //VPC的创建时间  创建不需要设置，查询时会返回
    private String CreationTime;


}
