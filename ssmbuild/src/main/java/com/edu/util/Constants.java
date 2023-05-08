package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
public class Constants {

    /***
     * request里发送和返回的token 的Key
     */
    public static final String USER_TOKEN_KEY = "tk-access-token";

    /***
     * redis存储token前缀key
     */
    public static final String REDIS_PREFIX_USER_TOKEN_KEY = "tk-access-token-";

    /***
     * tms的token Key
     */
    public static final String TMS_TOKEN_KEY = "tmsToken";

    /***
     * Token过期时间30(天)
     */
    //public static final long USER_TOKEN_EXPIRE_TIME_WITH_SECONDS = 60 * 60 * 24 * 30;
    public static final long USER_TOKEN_EXPIRE_TIME_WITH_SECONDS = 60 * 60 * 2;

    //C端token刷新时间
    public static final long USER_TOKEN_REFRESH_SECONDS = 60 * 60;
    // tms传递feign的key
    public static final String TMS_FEIGN_TOKEN = "tms-feign-token";

    // 前端用户feign的key
    public static final String FRONT_FEIGN_TOKEN = "front-feign-token";

    // 前端用户redis的key
    public static final String FRONT_USER_REDIS_KEY = "front-user-redis-key";

    // 虚拟企业主键(平台)
    public static final String VIRTUAL_CORP_PK = "00000000000000000001";
    // 商品item
    public static final String GOODS_ITEM_SEQ = "GOODS_ITEM_SEQ";
    // 商品sku
    public static final String GOODS_SKU_SEQ = "GOODS_SKU_SEQ";
    // 订单编号
    public static final String ORDER_SEQ = "ORDER_SEQ";
    // 企业账号编号key
    public static final String ACCOUNT_CORP_SEQ = "ACCOUNT_CORP_SEQ";
    // 供应商账号编号key
    public static final String ACCOUNT_PROVIDER_SEQ = "ACCOUNT_PROVIDER_SEQ";
    // 用户账号编号key
    public static final String ACCOUNT_USER_SEQ = "ACCOUNT_USER_SEQ";
    // 企业用户开户时的企业自定义编码，如果有值则使用传过来的值，如果没有值则默认为6个0
    public static final String DEFAULT_CORP_CUSTOM_CODE = "000000";
    //保险的企业自定义编码
    public static final String INSURANCE_CORP_CUSTOM_CODE = "100001";
    //体检的企业自定义编码
    public static final String PHYSICAL_EXAMINATION_CORP_CUSTOM_CODE = "100002";
    // 一天最后的时间
    public static final String END_TIME_FOR_DAY = " 23:59:59";
    //微信session_key前缀
    public static final String REDIS_PRE_WECHAT_SESSION_KEY = "wechat_code_session";

    // 下订单时商品信息规格名、规格值字段值分隔符
    public static final String SEPARATOR = "&";
    public static final String WECHAT_TOKEN_PREFIX = "wechat-token-";
    public static final String WECHAT_TOKEN_SUFFIX = "-userinfo";

    /***
     * Token过期时间2(天)
     */
    public static final long WECHAT_TOKEN_EXPIRE_TIME_WITH_SECONDS = 60 * 60 * 24 * 2;
    //小程序凭证
    public static final String MINIPROGRAM_TOKEN = "minProgarmToken";
    //小程序凭证过期时间
    public static final long MINIPROGRAM_TOKEN_EXPIRE_TIME_WITH_SECONDS = 60 * 59 * 2;


    //北方无名单投保活动
    public static final String ACTIVITY ="activity:";
    //中奖概率
    public static final String ACTIVITY_PROBABILITY =":probability";

    //活动奖品锁
    public static final String ACTIVITY_Status =":status";
    //活动奖品数量锁 :numStatus
    public static final String ACTIVITY_NUMSTATUS = ":numStatus";
    //活动奖品
    public static final String ACTIVITY_PRIZE = ":prize";
    //企业 邀请码对应 corpCheck
    public static final String ACTIVITY_CORPCHECK = "corpCheck:";

    /***
     * redis存储小程序openid
     */
    public static final String MINIPROGRAM_OPENID_KEY = "miniprogram-openid-key-";

    public static final String WXPAY_TYPE_MINIPROGRAM = "0";//小程序支付
    public static final String WXPAY_TYPE_APP = "1";//app支付

    // 赠险编号
    public static final String FREE_RISK_SEQ = "FREE_RISK_SEQ";

    /**
     * 第三方授权临时code码key
     */
    public static final String AUTH_CODE_KEY = "auth-code-";

    /**
     * 第三方授权access_token码key
     */
    public static final String AUTH_ACCESS_TOKEN_KEY = "auth-access-token-";

    /**
     * 订单评价集合
     */
    public static final String ORDER_EVALUATION_COLLECT_NAME = "orderItemEvaluationCollection";

    /**
     * 服务订单评价集合
     */
    public static final String HEALTH_ORDER_EVALUATION_COLLECT_NAME = "healthOrderEvaluationCollection";
}
