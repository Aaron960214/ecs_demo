package com.aliyun.ecs.acsClinet;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AcsClient {

    public IAcsClient getAcsClient(String regionId,String accessKeyId,String accessKeySecret){
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        return acsClient;
    }
}
