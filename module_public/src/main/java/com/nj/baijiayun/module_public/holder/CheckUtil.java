package com.nj.baijiayun.module_public.holder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Kai
 * @date 2020/8/3
 * @time 11:11
 */
public class CheckUtil {

    private CheckUtil() {
        // 这个类不能实例化
    }

    /**
     * 判断字符串不为空
     */
    public static boolean isNotEmpty(String s) {
        return (null != s) && (0 != s.length()) && (!"null".equals(s.toLowerCase()));
    }

    /**
     * 判断字符串不为空
     */
    public static boolean isNotEmpty(CharSequence s) {
        String text = String.valueOf(s);
        return (null != text) && (0 != text.length()) && (!"null".equals(text.toLowerCase()));
    }


    /**
     * 判断字符串为空
     */
    public static boolean isEmpty(String s) {
        return (null == s) || (0 == s.length()) || ("null".equals(s.toLowerCase())) || ("".equals(s));
    }

    /**
     * 判断List不为空
     */
    public static boolean  isListNotEmpty(List<?> list) {
        return (null != list) && (list.size() > 0);
    }

    /**
     * 判断List为空
     */
    public static boolean isListEmpty(List<?> list) {
        return (null == list) || (list.size() == 0);
    }

    /**
     * 判断JSON数组不为空
     */
    public static boolean isJSONArrayNotEmpty(JSONArray array) {
        return null != array && array.length() > 0;
    }

    /**
     * 判断JSON数组为空
     */
    public static boolean isJSONArrayEmpty(JSONArray array) {
        return null == array || array.length() == 0;
    }

    /**
     * 判断JSON对象不为空
     */
    public static boolean isJSONObjectNotEmpty(JSONObject object) {
        return null != object && object.length() > 0;
    }

    /**
     * 判断JSON对象为空
     */
    public static boolean isJSONObjectEmpty(JSONObject object) {
        return null == object || object.length() == 0;
    }

    /**
     * 判断是否包含字符串
     */
    public static boolean isContains(String s1, String s2) {
        if (null == s1 || null == s2) return false;
        return s1.contains(s2);
    }

}
