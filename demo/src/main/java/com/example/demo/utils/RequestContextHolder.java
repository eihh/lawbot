package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

public class RequestContextHolder {
    //两种场景  文书预测 和 文书摘要
    public static final String SCENE_YUCE = "yuce";
    public static final String SCENE_WENSHU = "wenshu";
    private static final ThreadLocal<Map<String, String>> contextHolder = new ThreadLocal<>();

    // 初始化Map
    private static Map<String, String> getContextMap() {
        Map<String, String> context = contextHolder.get();
        if (context == null) {
            context = new HashMap<>();
            contextHolder.set(context);
        }
        return context;
    }

    // 设置文件名（指定场景）
    public static void setFileName(String scene, String fileName) {
        getContextMap().put(scene, fileName);
    }

    // 获取文件名（指定场景）
    public static String getFileName(String scene) {
        return getContextMap().get(scene);
    }

    // 获取所有文件名
    public static Map<String, String> getAllFileNames() {
        return getContextMap();
    }

    // 清除指定场景
    public static void clear(String scene) {
        getContextMap().remove(scene);
    }

    // 清除所有
    public static void clearAll() {
        contextHolder.remove();
    }
}