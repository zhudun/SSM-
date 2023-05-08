package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionCutUtil {
    /**
     * 获取异常的具体信息
     *
     * @author fengshuonan
     * @Date 2017/3/30 9:21
     * @version 2.0
     */
    public static String getExceptionMsg(Exception e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
        } finally {
            try {
                if(sw != null) {
                    sw.close();
                }
            } catch (IOException e1) {
                // e1.printStackTrace();
            }
        }
        return sw.getBuffer().toString().replaceAll("\\$", "T");
    }

    /**
     * 获取异常的具体信息
     *
     * @author fengshuonan
     * @Date 2017/3/30 9:21
     * @version 2.0
     */
    public static String getExceptionMsg(Exception e, int len) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
        } finally {
            try {
                if(sw != null) {
                    sw.close();
                }
            } catch (IOException e1) {
                // e1.printStackTrace();
            }
        }
        String exception = sw.getBuffer().toString();
        return exception.replaceAll("\\$", "T").substring(0,
                (exception.length() - 1 > len ? len : exception.length() - 1));

    }
}
