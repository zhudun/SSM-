package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
    // 获取当前日期 10位
    public String getDate() {
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        return df1.format(date);
    }

    // 获取当前日期 月-日
    public String getMonthDayStr() {
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("MMdd");
        return df1.format(date);
    }

    // 获取当前时间 19位
    public String getTime() {
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df1.format(date);
    }

    // 获取dateTime类型的时间
    public Timestamp getDateTime() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    // 将Timestamp类型转换成可读类型
    public String getStringTime() {
        String str = "yyyy-MM-dd HH:mm:ss";
        Timestamp time = Timestamp.valueOf(str);
        String strn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        return strn;
    }

    // 获取当前日期 10位
    public String getDate(String format) {
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat(format);
        return df1.format(date);
    }
}
