package com.edu.exception;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
/***
 * 业务通用异常
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String msg) {
        super(msg);
    }
}
