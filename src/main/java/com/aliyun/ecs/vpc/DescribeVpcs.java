package com.aliyun.ecs.vpc;

import com.aliyun.ecs.acsClinet.AcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeVpcsRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeVpcsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class DescribeVpcs {

    static Logger logger = Logger.getLogger(DescribeVpcs.class);

    public static void main(String[] args) throws IOException, ClientException {
        Properties properties = new Properties();
        InputStream asStream = CreateVpc.class.getClassLoader().getResourceAsStream("config/account.properties");

        properties.load(asStream);

        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");
        String regionId = properties.getProperty("regionId");

        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");

        IAcsClient acsClient = new AcsClient().getAcsClient(regionId, accessKeyId, accessKeySecret);

        DescribeVpcsRequest vpcsRequest = new DescribeVpcsRequest();

        DescribeVpcsResponse acsResponse = acsClient.getAcsResponse(vpcsRequest);

        List<DescribeVpcsResponse.Vpc> vpcs = acsResponse.getVpcs();

        if(vpcs.isEmpty()){
            logger.info("磁盘列表为空。");
        }else{
            for (DescribeVpcsResponse.Vpc vpc:vpcs){
                System.out.println(vpc.getVpcName());
                System.out.println(vpc.getVRouterId());
            }
        }

        logger.info("Completed");
    }
}
