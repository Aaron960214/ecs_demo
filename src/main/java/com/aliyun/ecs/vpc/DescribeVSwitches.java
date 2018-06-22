package com.aliyun.ecs.vpc;

import com.aliyun.ecs.acsClinet.AcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeVSwitchesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeVSwitchesResponse;
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
 * <p>Company: </p>
 * <p>Description: </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public class DescribeVSwitches {

    static Logger logger = Logger.getLogger(DescribeVSwitches.class);
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

        DescribeVSwitchesRequest describeVSwitchesRequest = new DescribeVSwitchesRequest();

        describeVSwitchesRequest.setRegionId("cn-beijing");
        DescribeVSwitchesResponse acsResponse = null;
        try {
            acsResponse = acsClient.getAcsResponse(describeVSwitchesRequest);
            List<DescribeVSwitchesResponse.VSwitch> vSwitches = acsResponse.getVSwitches();


            if(vSwitches.isEmpty()){
                System.out.println("列表为空");
                logger.info("列表为空");
            }else{
                for(DescribeVSwitchesResponse.VSwitch vSwitch:vSwitches){
                    System.out.println("交换机所在的可用区: "+vSwitch.getZoneId());
                    System.out.println("交换机的名称: "+vSwitch.getVSwitchName());
                    System.out.println("是否是该可用区的默认交换机: "+vSwitch.getIsDefault());

                }
            }
        } catch (ClientException e) {
            logger.error(e);
        }



        logger.info("--结束--");
    }

}
