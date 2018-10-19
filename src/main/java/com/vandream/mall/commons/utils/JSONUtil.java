package com.vandream.mall.commons.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.vandream.mall.commons.annotation.JSONField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dingjie
 * @version 1.0
 * @date 2018-03-06
 */
public class JSONUtil {

    private static Gson gson = null;

    static {
        gson = new GsonBuilder().disableHtmlEscaping().create();// TODO
        // yyyy-MM-dd
        // HH:mm:ss
    }

    public static synchronized Gson newInstance() {
        if (gson == null) {
            gson = new GsonBuilder().disableHtmlEscaping().create();

        }
        return gson;
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T toBean(String json, Class<T> clz) {

        return gson.fromJson(json, clz);
    }

    public static <T> Map<String, T> toMap(String json, Class<T> clz) {
        Map<String, T> map = gson.fromJson(json, new TypeToken<Map<String, T>>() {
        }.getType());
        Map<String, T> result = new HashMap<String,T>();
        for (Map.Entry<String,T> entry : map.entrySet()) {
            result.put(entry.getKey(), gson.fromJson((String) entry.getValue(), clz));
        }
        return result;
    }

    public static Map<String, Object> toMap(String json) {
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        return map;
    }

    public static <T> List<T> toList(String json, Class<T> clz) {
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        List<T> list = new ArrayList<T>();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, clz));
        }
        return list;
    }

    public static <T> T toPOJO(String json, Class<T> clazz) {
        Gson gson = new GsonBuilder().setFieldNamingStrategy(new FieldNamingStrategy() {
            @Override
            public String translateName(Field f) {
                JSONField jf = f.getAnnotation(JSONField.class);
                String fieldName = null;
                if (jf == null) {
                    fieldName = f.getName();
                } else {
                    fieldName = jf.deserializeField();
                }
                if(StringUtil.isBlank(fieldName)){
                    fieldName = f.getName();
                }
                return fieldName;
            }
        }).create();

        return gson.fromJson(json, clazz);
    }

    public static <T> String toJSON(Object object, Class<T> clazz) {
        Gson gson = new GsonBuilder().setFieldNamingStrategy(new FieldNamingStrategy() {
            @Override
            public String translateName(Field f) {
                JSONField jf = f.getAnnotation(JSONField.class);
                String fieldName = null;
                if (jf == null) {
                    fieldName = f.getName();
                } else {
                    fieldName = jf.serializeField();
                }
                if(StringUtil.isBlank(fieldName)){
                    fieldName = f.getName();
                }
                return fieldName;
            }
        }).create();

        return gson.toJson(object,clazz);
    }
}
