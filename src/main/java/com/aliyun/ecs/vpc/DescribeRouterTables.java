package com.aliyun.ecs.vpc;

import com.aliyun.ecs.acsClinet.AcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeRouteTablesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeRouteTablesResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class DescribeRouterTables {

    static Logger logger = Logger.getLogger(DescribeRouters.class);

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        InputStream asStream = CreateVpc.class.getClassLoader().getResourceAsStream("config/account.properties");

        properties.load(asStream);

        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");
        String regionId = properties.getProperty("regionId");

        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("--开始--");

        IAcsClient acsClient = new AcsClient().getAcsClient(regionId, accessKeyId, accessKeySecret);

        DescribeRouteTablesRequest tablesRequest = new DescribeRouteTablesRequest();

        tablesRequest.setVRouterId("vrt-2zeol3yjw2ggpvp2fcsk1");

        DescribeRouteTablesResponse acsResponse = null;
        try {
            acsResponse = acsClient.getAcsResponse(tablesRequest);
            List<DescribeRouteTablesResponse.RouteTable> routeTables = acsResponse.getRouteTables();

            if(routeTables.isEmpty()){
                logger.info("列表为空");
            }else{
                for(DescribeRouteTablesResponse.RouteTable routeTable:routeTables){
                    System.out.println("路由表所在路由器的ID:"+routeTable.getVRouterId());
                    System.out.println("路由条目的详细信息: ");
                    List<DescribeRouteTablesResponse.RouteTable.RouteEntry> routeEntrys = routeTable.getRouteEntrys();
                    if(routeEntrys.isEmpty()){
                        System.out.println(" 为空 ");
                    }else {
                        for (DescribeRouteTablesResponse.RouteTable.RouteEntry routeEntry : routeEntrys){
                            System.out.println("路由条目所属路由表的ID："+routeEntry.getRouteTableId());
                            System.out.println("路由条目的目标网段："+routeEntry.getDestinationCidrBlock());
                            System.out.println("下一跳列表：");
                            List<DescribeRouteTablesResponse.RouteTable.RouteEntry.NextHop> nextHops = routeEntry.getNextHops();
                            if(nextHops.isEmpty()){
                                System.out.println(" 为空 ");
                            }else{
                                for(DescribeRouteTablesResponse.RouteTable.RouteEntry.NextHop nextHop:nextHops){
                                    System.out.println("下一跳的类型: "+nextHop.getNextHopType());
                                    System.out.println("下一跳实例的ID: "+nextHop.getNextHopId());
                                }
                            }
                        }
                    }
                }
            }
        } catch (ClientException e) {
            logger.error(e);
        }

        logger.info("--结束--");


    }
}
