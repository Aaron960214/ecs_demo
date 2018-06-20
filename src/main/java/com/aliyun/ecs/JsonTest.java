package com.aliyun.ecs;

import com.google.gson.JsonArray;

public class JsonTest {
    public static void main(String[] args) {

        JsonArray array = new JsonArray();
        array.add("The specified parameter diskIds is not valid");
        array.add("The specified parameter diskIds is not valid");
        System.out.println(array.toString());

    }
}
