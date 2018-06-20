package com.aliyun.ecs.instance;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.List;

public class HelloInstance {

    private static String accessKeyId = "your accessKeyId";
    private static String accessKeySecret = "your accessKeySecret";
    private static String regionId = "cn-beijing";

    static Logger logger = Logger.getLogger(HelloInstance.class);
    public static void main(String[] args) {
        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");
        // 创建 Profile。生成 IClientProfile 的对象 profile，该对象存放 AccessKeyID 和 AccessKeySecret 和默认的地域信息
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        //创建 Client。从 IClientProfile 类中再生成 IAcsClient 的对象 client，后续获得 response 都需要从 IClientProfile 中获得。
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //创建 Request。创建一个对应方法的 Request，类的命名规则一般为 API 的方法名加上 “Request”,查询磁盘列表
        DescribeInstancesRequest instancesRequest = new DescribeInstancesRequest();
        //设置必要参数
        //获取对request的响应
        DescribeInstancesResponse acsResponse;
        try {
            acsResponse = acsClient.getAcsResponse(instancesRequest);
            List<DescribeInstancesResponse.Instance> instances = acsResponse.getInstances();
            if(instances==null){
                System.out.println("实例列表为空。");
                logger.info("实例列表为空。");
            }
            else {
                for ( DescribeInstancesResponse.Instance instance : instances){
                    System.out.println("实例ID："+instance.getInstanceId());
                    System.out.println("实例名称："+instance.getInstanceName());
                    logger.info("实例ID："+instance.getInstanceId());
                }
            }

        } catch (ClientException e) {
            logger.info(e);
        }
        logger.info("Completed");
    }
}
