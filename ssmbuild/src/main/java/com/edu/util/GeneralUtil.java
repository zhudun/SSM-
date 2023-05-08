package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GeneralUtil {


    //获取unix时间戳
    public static String getTs(){
        //初始化时区对象，北京时间是UTC+8，所以入参为8
        ZoneOffset zoneOffset=ZoneOffset.ofHours(8);
        //初始化LocalDateTime对象
        LocalDateTime localDateTime=LocalDateTime.now();
        //获取LocalDateTime对象对应时区的Unix时间戳
        return Long.toString(localDateTime.toEpochSecond(zoneOffset));
    }

    //获取随机数
    public static String getNonce(){
        String nonce = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
        return nonce;
    }

    public static String maskMobile(String mobile){
        String mask = null;
        if(StringUtil.isNotEmpty(mobile) && mobile.length() == 11){
            mask = mobile.substring(0,3) + "****" + mobile.substring(mobile.length()-4);
        }else{
            mask = mobile;
        }
        return mask;
    }

    public static String maskCardNo(String cardNo){

        if(StringUtil.isEmpty(cardNo)){
            return null;
        }

        if(cardNo.length() > 6){
            String str = cardNo.substring(0,3) + getMask(cardNo.length() - 6) + cardNo.substring(cardNo.length()-4);
            return str;
        }
        return cardNo;
    }



    //获得签名
    public static String getSign(List<String> list){
        Collections.sort(list);

        String signStr = "";
        for(String str : list){
            signStr += str;
        }

        signStr = DigestUtils.sha1Hex(signStr);

        return signStr;
    }

    private static String getMask(int len){
        if(len <= 0){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(int i=0; i < len; i++){
            sb.append("*");
        }
        return sb.toString();
    }
}
