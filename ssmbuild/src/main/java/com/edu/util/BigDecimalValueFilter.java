package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import com.alibaba.fastjson.serializer.ValueFilter;

import java.math.BigDecimal;

/**
 * @program: merage_2
 * @Date: 2019/11/29 16:55
 * @Author: Mr.Liu
 * @Description:
 */
public class BigDecimalValueFilter implements ValueFilter {
    @Override
    public Object process(Object object, String name, Object value) {
        if (null != value && value instanceof BigDecimal) {
            return ((BigDecimal) value).setScale( 2 ).toString();
        }
        return value;
    }
}
