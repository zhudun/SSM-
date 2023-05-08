package com.edu.interceptor;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import com.edu.exception.SysException;
import com.edu.exception.BusinessException;
import com.edu.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likai
 * @Date 2018/10/18
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler({BusinessException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    public Result businessException(Exception e) {
        Result result = new Result(false);
        result.setResultCode("9999");
        result.setErrorMessage(e.getMessage());
        //result.setSuccessMessage("对不起,您没权限操作!");
        return result;

    }

    @ExceptionHandler(SysException.class)
    @ResponseBody
    public Result sysException(Exception e) {
        Result result = new Result(false);
        result.setErrorMessage("系统错误");
        return result;

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result unCatchException(Exception e) {
        Result result = new Result(false);
        result.setResultCode("9999");
        result.setErrorMessage("系统错误，请联系管理员");
        log.error("错误信息",e);
        return result;

    }

}
