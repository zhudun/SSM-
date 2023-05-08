package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;

/**
 * 服务报告token加密
 */
@Component
public class DESClaimUtil {

    private static final String AES_CLAIM_KEY = "tfl2020@";

    /**
     * 加密方法 1
     * 对给定的字符串以指定的编码方式和密钥进行加密
     * @param srcStr 待加密的字符串
     */
    public String encrypt(String srcStr) {
        Charset charset = Charset.forName("utf8");
        String sKey = AES_CLAIM_KEY;
        byte[] src = srcStr.getBytes(charset);
        byte[] buf = DESClaimUtil.encrypt(src, sKey);
        return DESClaimUtil.parseByte2HexStr(buf);
    }

    /**
     * 加密方法 2
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 加密方法 3
     * 解密
     * @param data
     * @param sKey
     * @return
     */
    public static byte[] encrypt(byte[] data, String sKey) {
        try {
            byte[] key = sKey.getBytes();
            IvParameterSpec iv = new IvParameterSpec(key);
            DESKeySpec desKey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 解密方法 1
     * 对给定的密文以指定的编码方式和密钥进行解密
     * @param hexStr 需要解密的密文
     * @return 解密后的原文
     * @throws Exception
     */
    public String decrypt(String hexStr) throws Exception {
        Charset charset = Charset.forName("utf8");
        String sKey = AES_CLAIM_KEY;
        byte[] src = DESClaimUtil.parseHexStr2Byte(hexStr);
        byte[] buf = DESClaimUtil.decrypt(src, sKey);
        return new String(buf, charset);
    }

    /**
     * 解密方法 2
     * @param src
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String sKey) throws Exception {
        byte[] key = sKey.getBytes();
        // 初始化向量
        IvParameterSpec iv = new IvParameterSpec(key);
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key);
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

    /**
     * 解密方法 3
     * 将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * test
     * @param args
     */
    public static void main(String[] args){
        DESClaimUtil util = new DESClaimUtil();
        String testStr = "0001AA10000000008657";
        System.out.println("加密前：" + testStr);

        String encodeStr = util.encrypt(testStr);
        System.out.println("加密数据：" + encodeStr);
        String decodeStr = null;
        try {
            decodeStr = util.decrypt("4445A57D818B34B7003B21F2919961D3728EB2D62DD12597999");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("解密后数据： == " + decodeStr);
    }


}
