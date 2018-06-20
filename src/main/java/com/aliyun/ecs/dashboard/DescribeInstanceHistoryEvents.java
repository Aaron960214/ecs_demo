package com.aliyun.ecs.dashboard;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksFullStatusRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksFullStatusResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.List;

public class DescribeInstanceHistoryEvents {

    private static String accessKeyId = "LTAIdC9fh1tgCeWpz";
    private static String accessKeySecret = "LTAIdC9fh1tgCeWpz";
    private static String regionId = "cn-beijing";
    static Logger logger = Logger.getLogger(DescribeInstanceHistoryEvents.class);

    public static void main(String[] args) {

        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");


    }

}
