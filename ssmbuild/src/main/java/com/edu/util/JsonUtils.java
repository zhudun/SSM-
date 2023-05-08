package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {

    public JsonUtils() {}

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return JSONObject.parseObject(jsonString, clazz);
    }

    public static <T> List<T> fromJsonArray(String jsonArray, Class<T> clazz) {
        return JSONObject.parseArray(jsonArray, clazz);
    }

    public static Collection fromJsonArrayBy(String jsonArray, Class collectionClazz) {

        return JSONObject.parseArray(jsonArray, collectionClazz);
    }

    public static String toJson(Object object) {
        return JSONObject.toJSONString(object);
    }

    // public static void setDateFormat(String pattern) {
    // JSONObject.(pattern);
    // }

    public static String returnWhenError(String errMsg, Object data) {
        return returnResult(errMsg, data, 0);
    }

    public static String returnWhenSuccess(String errMsg, Object data) {
        return returnResult(errMsg, data, 1);
    }

    public static String returnResult(String errMsg, Object data, int status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("msg", errMsg);
        jsonObject.put("data",
                JSONObject.toJSONString(data, new SerializerFeature[] {SerializerFeature.WriteMapNullValue}));
        return jsonObject.toString();
    }
}
