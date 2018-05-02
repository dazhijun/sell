package com.yan.sell.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Statement;

public class JsonUtil {
    public static String tojson(Object object){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson=gsonBuilder.create();
        return gson.toJson(object);
    }
}
