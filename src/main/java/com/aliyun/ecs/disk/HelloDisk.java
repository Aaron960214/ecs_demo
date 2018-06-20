package com.aliyun.ecs.disk;


import com.aliyun.ecs.utils.Utils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.JsonArray;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.List;

public class HelloDisk {

    private static String accessKeyId = "LTAIdC9h1tgCeWpz";
    private static String accessKeySecret = "LTAIdC9fh1tgCeWpz";
    private static String regionId = "cn-beijing";

    static Logger logger = Logger.getLogger(HelloDisk.class);
    public static void main(String[] args) {
        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");
        // 创建 Profile。生成 IClientProfile 的对象 profile，该对象存放 AccessKeyID 和 AccessKeySecret 和默认的地域信息
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        //创建 Client。从 IClientProfile 类中再生成 IAcsClient 的对象 client，后续获得 response 都需要从 IClientProfile 中获得。
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //创建 Request。创建一个对应方法的 Request，类的命名规则一般为 API 的方法名加上 “Request”,查询磁盘列表
        DescribeDisksRequest discribe = new DescribeDisksRequest();
        //设置必要参数


        discribe.setDiskIds(Utils.toJson(new String[]{"d-2zebziliodb2ylftfhq5"}));
        //获取对request的响应
        DescribeDisksResponse acsResponse;
        try {
            acsResponse = acsClient.getAcsResponse(discribe);
            List<DescribeDisksResponse.Disk> disks = acsResponse.getDisks();

            if(null == disks || disks.isEmpty()){
                System.out.println("磁盘列表为空。");
                logger.info("磁盘列表为空。");
            }
            else {
                for (DescribeDisksResponse.Disk disk : disks){
                    System.out.println("云盘ID："+disk.getDiskId());
                    System.out.println("云盘名称："+disk.getDiskName());logger.info("云盘ID："+disk.getDiskId());

                }
            }

        } catch (ClientException e) {
            logger.info(e);
        }
        logger.info("Completed");
    }
}
