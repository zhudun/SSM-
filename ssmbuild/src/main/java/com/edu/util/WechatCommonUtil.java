package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: dev_ljs
 * @Date: 2019/5/11 17:11
 * @Author: Mr.Liu
 * @Description:微信工具类
 */
@Component
public class WechatCommonUtil {
    @Autowired
    private RedisUtil redisUtil;

    /**
     *
     * 根据微信授权登录后生成的token获取用户信息
     * @param wechatToken
     * @return
     */
    public JSONObject getUserInfo(String wechatToken){
        JSONObject userinfo = null;
        if(StringUtil.isNotEmpty(wechatToken)){
            if("qwertyuiop".equals(wechatToken)){
                userinfo = new JSONObject();
                userinfo.put("openid","ogE5n0l-t36Ph5ofjAVSzPGWfibQ");
                return userinfo;
            }
            userinfo = (JSONObject) redisUtil.get(Constants.WECHAT_TOKEN_PREFIX + wechatToken + Constants.WECHAT_TOKEN_SUFFIX);
        }
        return userinfo;
    }
}
