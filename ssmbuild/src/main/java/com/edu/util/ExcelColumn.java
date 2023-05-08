package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExcelColumn {
    /**
     * excel表头
     * @return
     */
    String name() default "表头";

    /**
     * 导出的列的位置,从0开始
     * @return
     */
    int order() default 0;

    /**
     * 导出的列的宽度
     * @return
     */
    int width() default 5000;
}
