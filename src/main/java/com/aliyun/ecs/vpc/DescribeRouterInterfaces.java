package com.aliyun.ecs.vpc;

import com.aliyun.ecs.acsClinet.AcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeRouterInterfacesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeRouterInterfacesResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company:  </p>
 * <p>Description: 查询指定地域内的路由器接口 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public class DescribeRouterInterfaces {
    static Logger logger = Logger.getLogger(DescribeRouterInterfaces.class);

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

        DescribeRouterInterfacesRequest request = new DescribeRouterInterfacesRequest();

        request.setRegionId("cn-beijing");

        DescribeRouterInterfacesResponse acsResponse = null;
        try {
            acsResponse = acsClient.getAcsResponse(request);
            List<DescribeRouterInterfacesResponse.RouterInterfaceType> routerInterfaceSet = acsResponse.getRouterInterfaceSet();

            if(routerInterfaceSet.isEmpty()){
                System.out.println("kong ");
            }else{
                System.out.println(routerInterfaceSet.toString());
            }

        } catch (ClientException e) {

            logger.error(e);
        }


        logger.info("--结束--");
    }

}
