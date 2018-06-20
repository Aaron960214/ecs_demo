package com.aliyun.ecs.vpc;

import com.aliyun.ecs.acsClinet.AcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.CreateVpcRequest;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CreateVpc {

    static Logger logger = Logger.getLogger(CreateVpc.class);

    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        InputStream asStream = CreateVpc.class.getClassLoader().getResourceAsStream("config/account.properties");

        properties.load(asStream);

        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");
        String regionId = properties.getProperty("regionId");

//        System.out.println(accessKeyId);
        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");

        IAcsClient acsClient = new AcsClient().getAcsClient(regionId, accessKeyId, accessKeySecret);

        CreateVpcRequest createVpcRequest = new CreateVpcRequest();


//        createVpcRequest.setCidrBlock();

    }
}
