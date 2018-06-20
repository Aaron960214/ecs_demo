package com.aliyun.ecs.dashboard;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksFullStatusRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksFullStatusResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.List;

public class DescribeDisksFullStatus {
    private static String accessKeyId = "LTAIdC9fh1tgCeWpz";
    private static String accessKeySecret = "LTAIdC9fh1tgCeWpz";
    private static String regionId = "cn-beijing";
    static Logger logger = Logger.getLogger(DescribeDisksFullStatus.class);
       public static void main(String[] args) {

        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");
        IAcsClient acsClient = getAcsClient();

        DescribeDisksFullStatusRequest describeDisksFullStatusRequest = new DescribeDisksFullStatusRequest();
        ArrayList<String> ids = new ArrayList<String>();
        ids.add("d-2zeh5tgj6x2neqyxk6xh");
//        describeDisksFullStatusRequest.setDiskIds(ids);

        DescribeDisksFullStatusResponse response = null;
        try {
            response = acsClient.getAcsResponse(describeDisksFullStatusRequest);
            List<DescribeDisksFullStatusResponse.DiskFullStatusType> diskFullStatusSet = response.getDiskFullStatusSet();

            for (DescribeDisksFullStatusResponse.DiskFullStatusType type :
                    diskFullStatusSet) {
                for(DescribeDisksFullStatusResponse.DiskFullStatusType.DiskEventType diskEventType:type.getDiskEventSet()){
                    System.out.println("磁盘的事件类型代码："+diskEventType.getEventType().getCode());
                    System.out.println("磁盘的事件类型名称："+diskEventType.getEventType().getName());
                }

                System.out.println("磁盘的健康状态： "+type.getHealthStatus().getName());
            }
        } catch (ClientException e) {
            logger.info(e);
        }

        logger.info("Completed");

    }

    public static IAcsClient getAcsClient()  {
        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);
        return new DefaultAcsClient(profile);
    }

}
