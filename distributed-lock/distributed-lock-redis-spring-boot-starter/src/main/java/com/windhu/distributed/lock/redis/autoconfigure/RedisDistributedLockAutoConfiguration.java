/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.redis.autoconfigure;

import com.windhu.distributed.lock.redis.impls.RedisDistributedLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * <p>redis分布式锁自动配置</p>
 *
 * @author : wind-hu
 **/
@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisDistributedLockAutoConfiguration {
    /**
     * redis分布式锁
     *
     * @param stringRedisTemplate string格式redis操作对象
     * @return redis分布式锁
     */
    @Bean
    public RedisDistributedLock redisDistributedLock(StringRedisTemplate stringRedisTemplate) {
        return new RedisDistributedLock(stringRedisTemplate);
    }
}
