/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.redis.impls;

import com.windhu.distributed.lock.abstracts.AbstractDistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

/**
 * <p>redis分布式锁</p>
 *
 * @author : wind-hu
 **/
@Order(2)
@RequiredArgsConstructor
public class RedisDistributedLock extends AbstractDistributedLock<String> {
    private static final String REDIS_ACQUIRE_SCRIPT =
            "if redis.call('setNx',KEYS[1],ARGV[1]) " +
                    "then if redis.call('get',KEYS[1])==ARGV[1]" +
                    " then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";

    private static final String REDIS_RELEASE_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "return redis.call('del', KEYS[1]) else return 0 end";

    private static final long SUCCESS = 1L;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public String acquire(String lockKey, String lockValue, long expireTime) {
        RedisScript<Long> acquireScript = new DefaultRedisScript<>(REDIS_ACQUIRE_SCRIPT, Long.class);
        Long acquireResult = stringRedisTemplate.execute(acquireScript,
                Collections.singletonList(lockKey),
                lockValue, String.valueOf(expireTime));
        return acquireResult == SUCCESS ? String.valueOf(acquireResult) : null;
    }

    @Override
    public boolean release(String lockKey, String lockValue,String lockObject) {
        RedisScript<Long> releaseScript = new DefaultRedisScript<>(REDIS_RELEASE_SCRIPT, Long.class);
        Long releaseResult = stringRedisTemplate.execute(releaseScript,
                Collections.singletonList(lockKey),
                lockValue);
        return releaseResult == SUCCESS;
    }
}
