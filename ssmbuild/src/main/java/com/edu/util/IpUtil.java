package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import javax.servlet.http.HttpServletRequest;

/**
 * @author yangtao39
 * @date 2019/9/11 19:59
 */
public class IpUtil {
    public static String getIpAddress(HttpServletRequest request) {
        String separator = ",";
        String unknown = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(separator)) {
            return ip.split(",")[0];
        } else {
            return ip;
        }
    }
}
