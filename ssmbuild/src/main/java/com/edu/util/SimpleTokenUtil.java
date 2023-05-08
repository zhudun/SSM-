package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class SimpleTokenUtil {

    private static final char[] hexCode =
            "0123456789abcdefghijklmnopqrstuvwsyzABCDEFGHIJKLMNOPQRSTUVWSYZ".toCharArray();

    /*
     * 单例设计模式（保证类的对象在内存中只有一个）1、把类的构造函数私有2、自己创建一个类的对象3、对外提供一个公共的方法，返回类的对象
     */
    private SimpleTokenUtil() {}

    private static final SimpleTokenUtil instance = new SimpleTokenUtil();

    /**
     * 获取实例
     *
     * @return
     */
    public static SimpleTokenUtil getInstance() {
        return instance;
    }

    public String createToken() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 生成Token Token：Nv6RRuGEVvmGjB+jimI/gw==
     *
     * @return
     */
    public String createTokenOld() { // checkException
        // 7346734837483 834u938493493849384 43434384
        String token = UUID.randomUUID().toString().replaceAll("-", "") + "";
        // 数据指纹 128位长 16个字节 md5
        try {
            // MessageDigest md = MessageDigest.getInstance("md5");
            // byte md5[] = md.digest(token.getBytes());
            // base64编码--任意二进制编码明文字符 adfsdfsdfsf
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(token.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 创建新的token
     *
     * @param jsessionID
     * 传入user的jsessionID
     * @return
     */
    public String createToken(String jsessionID) { // checkException
        // 数据指纹 128位长 16个字节 md5
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(jsessionID.getBytes());
            // base64编码--任意二进制编码明文字符 adfsdfsdfsf
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createToken1() {
        return new String(toHexString(UUID.randomUUID().toString().getBytes()));
    }

    private String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    /**
     * <p>
     * 随机生成 access token 值的函数，怕有重复问题暂不敢用
     * </p >
     *
     * @param length
     * 需要生成的 access token 长度
     * @return String 类型为字符串的accesstoken值
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        Random randGen = new Random();
        char[] numbersAndLetters =
                ("0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(64)];
        }
        return new String(randBuffer);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    System.out.println(SimpleTokenUtil.getInstance().createToken());
                }
            }).start();
        }
    }

}
