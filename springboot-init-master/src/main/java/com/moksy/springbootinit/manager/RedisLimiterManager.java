package com.moksy.springbootinit.manager;

import com.moksy.springbootinit.common.ErrorCode;
import com.moksy.springbootinit.config.RedissonConfig;
import com.moksy.springbootinit.exception.BusinessException;
import com.moksy.springbootinit.exception.ThrowUtils;
import jakarta.annotation.Resource;
import net.bytebuddy.implementation.bytecode.Throw;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

/*
 * 专门提供限流基础服务的（一个通用的能力）
 */
@Service
public class RedisLimiterManager {
    @Resource
    private RedissonClient redissonClient;

    /*
     * 进行限流操作
     * */
    public void doRateLimit(String key) {
        //调用方法,创建限流器，每秒最多访问2次
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 限流器的统计规则(每秒2个请求;连续的请求,最多只能有1个请求被允许通过)
        // RateType.OVERALL表示速率限制作用于整个令牌桶,即限制所有请求的速率
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);
        // 每当一个操作来了后，请求一个令牌
        boolean canOp = rateLimiter.tryAcquire(1);
        // 如果没有令牌,还想执行操作,就抛出异常
        if(!canOp){
            throw new BusinessException(ErrorCode.To_MANY_REQUEST);
        }
    }
}

