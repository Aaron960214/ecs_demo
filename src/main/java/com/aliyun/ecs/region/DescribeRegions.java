package com.aliyun.ecs.region;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeRegionsRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeRegionsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.List;

public class DescribeRegions {

    private static String accessKeyId = "LTAIdC9fh1tgCeWpz";
    private static String accessKeySecret = "LTAIdC9fh1tgCeWpz";
    private static String regionId = "cn-hangzhou";
    static Logger logger = Logger.getLogger(DescribeRegions.class);


    public static void main(String[] args) {
        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");
        // 创建 Profile。生成 IClientProfile 的对象 profile，该对象存放 AccessKeyID 和 AccessKeySecret 和默认的地域信息
//        IClientProfile profile = DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);

        IAcsClient acsClient = getAcsClient();

        //创建 Client。从 IClientProfile 类中再生成 IAcsClient 的对象 client，后续获得 response 都需要从 IClientProfile 中获得。
//        IAcsClient acsClient = new DefaultAcsClient(profile);
        //创建 Request。创建一个对应方法的 Request，类的命名规则一般为 API 的方法名加上 “Request”,查询磁盘列表
        DescribeRegionsRequest regionsRequest = new DescribeRegionsRequest();

        DescribeRegionsResponse regionsResponse;

        try {
            regionsResponse = acsClient.getAcsResponse(regionsRequest);

            List<DescribeRegionsResponse.Region> regions = regionsResponse.getRegions();

            for (DescribeRegionsResponse.Region region :
                    regions) {
                System.out.println("地域： "+region.getLocalName());
                System.out.println("地域id： "+region.getRegionId());
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
