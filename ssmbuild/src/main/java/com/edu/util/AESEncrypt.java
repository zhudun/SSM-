package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESEncrypt {

    private AESEncrypt() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 根据key值解密data
     * @param data 加密后的sign值
     * @param key  appSecret系统授权码
     * @return 解密后的sign值
     */
    public static String decryptByAES(String data, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Key secureKey = initKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, secureKey);
        byte[] dataByteArr = base64Decrypt(cipher.doFinal(base64DecryptUrlSafe(data.getBytes())));
        return new String(dataByteArr);
    }

    /**
     * 根据key值加密data
     * @param data 需要加密的值
     * @param key  appSecret系统授权码
     * @return 加密后的sign值
     */
    public static String encryptByAES(String data, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Key secureKey = initKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, secureKey);
        byte[] dataByteArr = base64EncryptUrlSafe(cipher.doFinal(base64Encrypt(data.getBytes())));
        return new String(dataByteArr);
    }

    public static byte[] base64Encrypt(byte[] data){
        return Base64.getEncoder().encode(data);
    }

    public static byte[] base64Decrypt(byte[] data){
        return Base64.getDecoder().decode(data);
    }

    public static byte[] base64EncryptUrlSafe(byte[] data) {
        return Base64.getUrlEncoder().encode(data);
    }

    public static byte[] base64DecryptUrlSafe(byte[] data){
        return Base64.getUrlDecoder().decode(data);
    }

    public static String sort(String str) {
        char[] strArray = str.toCharArray();

        for (int resultStr = 0; resultStr < strArray.length; ++resultStr) {
            for (int j = 0; j < resultStr; ++j) {
                if (strArray[resultStr] < strArray[j]) {
                    char temp = strArray[resultStr];
                    strArray[resultStr] = strArray[j];
                    strArray[j] = temp;
                }
            }
        }
        return new String(strArray);
    }

    private static Key initKey(String key) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(sort(key).getBytes());
        keyGen.init(128, secureRandom);
        SecretKey secretKey = keyGen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return new SecretKeySpec(encoded, "AES");
    }
}
