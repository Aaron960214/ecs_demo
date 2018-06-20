package com.aliyun.ecs.region;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.List;

public class DescribeZones {
    private static String accessKeyId = "LTAIdC9fh1tgCeWpz";
    private static String accessKeySecret = "LTAIdC9fh1tgCeWpz";
    private static String regionId = "cn-beijing";
    static Logger logger = Logger.getLogger(DescribeZones.class);

    public static void main(String[] args) {
        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");
        // 创建 Profile。生成 IClientProfile 的对象 profile，该对象存放 AccessKeyID 和 AccessKeySecret 和默认的地域信息
        IClientProfile profile = DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);



        //创建 Client。从 IClientProfile 类中再生成 IAcsClient 的对象 client，后续获得 response 都需要从 IClientProfile 中获得。
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //创建 Request。创建一个对应方法的 Request，类的命名规则一般为 API 的方法名加上 “Request”,查询磁盘列表
        DescribeZonesRequest zonesRequest = new DescribeZonesRequest();

        DescribeZonesResponse zonesResponse;

        try {
            zonesResponse = acsClient.getAcsResponse(zonesRequest);

            List<DescribeZonesResponse.Zone> zones = zonesResponse.getZones();

            for (DescribeZonesResponse.Zone zone :
                    zones) {
                System.out.println("地域： "+zone.getLocalName());
                System.out.println("地域Id： "+zone.getZoneId());
            }


        } catch (ClientException e) {
            logger.info(e);
        }
        logger.info("Completed");
    }

}
