package com.aliyun.ecs.disk;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.AttachDiskRequest;
import com.aliyuncs.ecs.model.v20140526.AttachDiskResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Attach {

    private static String accessKeyId = "your accessKeyId";
    private static String accessKeySecret = "your accessKeySecret";
    private static String regionId = "cn-beijing";


    static Logger logger = Logger.getLogger(Attach.class);
    public static void main(String[] args) {
        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");
        // 创建 Profile。生成 IClientProfile 的对象 profile，该对象存放 AccessKeyID 和 AccessKeySecret 和默认的地域信息
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        //创建 Client。从 IClientProfile 类中再生成 IAcsClient 的对象 client，后续获得 response 都需要从 IClientProfile 中获得。
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //创建 Request。创建一个对应方法的 Request，类的命名规则一般为 API 的方法名加上 “Request”,查询磁盘列表
        AttachDiskRequest attachDiskRequest = new AttachDiskRequest();
        //设置必要参数
        attachDiskRequest.setDiskId("d-2ze7d3catjoo2ct70wer");
        attachDiskRequest.setInstanceId("i-2ze7z98sxvwda1eby0nk");
//        attachDiskRequest.setDiskId("");
//        attachDiskRequest.setInstanceId("");
        //获取对request的响应
        AttachDiskResponse acsResponse;
        try {
            acsClient.getAcsResponse(attachDiskRequest);

        } catch (ClientException e) {
            logger.info(e);
        }
        logger.info("Completed");
    }
}
