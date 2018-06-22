package com.aliyun.ecs.vpc;

import com.aliyun.ecs.acsClinet.AcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeVRoutersRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeVRoutersResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class DescribeRouters {

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

        DescribeVRoutersRequest request = new DescribeVRoutersRequest();
        DescribeVRoutersResponse acsResponse = null;
        try {
            acsResponse = acsClient.getAcsResponse(request);
            List<DescribeVRoutersResponse.VRouter> vRouters = acsResponse.getVRouters();

            if(vRouters.isEmpty()){
                logger.info("列表为空");
            }else{
                for (DescribeVRoutersResponse.VRouter router:vRouters) {
                    logger.info("路由器姓名: "+router.getVRouterName());
                    logger.info("路由器id: "+router.getVRouterId());
                    logger.info("路由表id：:"+router.getRouteTableIds());
                    System.out.println("路由器姓名: "+router.getVRouterName());
                    System.out.println("vpcId: "+router.getVpcId());
                    System.out.println("路由表id：:"+router.getRouteTableIds());
                }
            }
        } catch (ClientException e) {
            logger.error(e);
        }


        logger.info("--结束--");

    }
}
