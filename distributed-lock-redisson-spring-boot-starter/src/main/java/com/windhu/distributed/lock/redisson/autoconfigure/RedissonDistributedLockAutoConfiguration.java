/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.redisson.autoconfigure;

import com.windhu.distributed.lock.redisson.impls.RedissonDistributedLock;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Redisson实现的分布式锁自动配置</p>
 *
 * @author : wind-hu
 **/
@Configuration
@ConditionalOnClass(Redisson.class)
public class RedissonDistributedLockAutoConfiguration {
    @Bean
    public RedissonDistributedLock redissonDistributedLock(RedissonClient redissonClient) {
        return new RedissonDistributedLock(redissonClient);
    }
}
