package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OIDGenerateUtil {

    // Mysql主键生成
    private static final String[] chars = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    /**
     * 生成20位随机id
     *
     * @return
     */
    public static String generateOID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 20; i++) {
            String str = uuid.substring(i, i + 4);
            // 将16进制数据转为int类型数据
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    // 测试代码
    public static void main(String[] args) {
        ConcurrentHashMap cmap = new ConcurrentHashMap(2048);
        // 测试100线程并发下是否会产生重复ID
        for (int i = 0; i < 100; i++) {
            Thread tr = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        String key = generateOID();
                        if (cmap.get(key) == null) {
                            cmap.put(key, key);
                        } else {
                            System.out.println("产生重复的key:" + key);
                        }
                    }
                }
            });
            tr.start();
        }
        // 测试结果表明基本不会生成重复id
    }

    // 生成15位随机数字
    public static String getRandomNumber() {
        StringBuffer shortBuffer = new StringBuffer();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String ff = format.format(date).replace("-", "");// 当前日期
        String hh = String.valueOf(Math.random()).replace(".", "").substring(1, 8);// 七位随机数
        shortBuffer.append(ff + hh);
        return shortBuffer.toString();
    }
}
