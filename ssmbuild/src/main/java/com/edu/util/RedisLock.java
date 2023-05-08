package com.edu.util;

/**
 * @Author: YangZhen
 * @Date: 2023/5/8
 * @Description:
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @program: dev1.2
 * @Date: 2019/11/18 11:23
 * @Author: Mr.Liu
 * @Description: redis分布式锁
 */
@Component
public class RedisLock {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final Long SUCCESS = 1L;
    private static final int DEFAULT_EXPIRE_TIME = 300;//默认失效时间5分钟
    private final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    /**
     * 获取锁
     * @param lockKey
     * @param value value值要确保唯一性
     * @param expireTime：单位-秒
     * @return
     */
    public boolean getLock(String lockKey, String value, int expireTime){
        boolean ret = false;
        try{
            String script = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";

            RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);

            Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),value,expireTime);

            if(SUCCESS.equals(result)){
                logger.info("获取锁成功===");
                return true;
            }

        }catch(Exception e){
            logger.error("获取锁异常",e);
        }
        return ret;
    }

    /**
     * 获取锁(使用默认失效时间)
     * @param lockKey
     * @param value value值要确保唯一性
     * @return
     */
    public boolean getLock(String lockKey, String value){
        return getLock(lockKey,value,DEFAULT_EXPIRE_TIME);
    }

    /**
     * 释放锁
     * @param lockKey
     * @param value
     * @return
     */
    public boolean releaseLock(String lockKey, String value){

        //通过lockKey和value确保释放的为当前线程获取的锁，避免由于超时而释放其他线程的锁
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);

        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),value);
        if(SUCCESS.equals(result)) {
            logger.info("释放锁成功===");
            return true;
        }

        return false;
    }

}
