package com.freedom.chatmodule.tools.danmaku;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @Author kede·W  on  2023/3/26
 */
public class ByteTools {

    /**
     *
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String byteArrayToString(byte[] content) throws UnsupportedEncodingException {
        return new String(content, StandardCharsets.UTF_8);
    }

    public <T> T stringTo(String str, Class<T> tClass) throws UnsupportedEncodingException {
        return JSON.parseObject(str,tClass);
    }

    /**
     * 将byte数组转换为 int（32位）
     * @param byteArray
     * @return
     */
    public static int getInt(byte[] byteArray){
        return ByteBuffer.wrap(byteArray).getInt();
    }

    /**
     * 字符串转换为 16进制表示，
     * @param str
     * @return
     */
    public static String strToHexString(String str){
        byte[] bytes = str.getBytes();
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    /**
     * 整数转换为 16进制表示，
     * @param num
     * @return
     */
    public static String intToHexString(Integer num){
        String hex = Integer.toHexString(num);
        return hex;
    }


}
