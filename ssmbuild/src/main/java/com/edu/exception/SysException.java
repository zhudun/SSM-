package com.edu.exception;


/*
 * @Description 系统错误异常
 * @author itw_lizf
 * @Date 2022/8/12
 **/
public class SysException extends RuntimeException{
    public SysException(String msg) {
        super(msg);
    }
}
