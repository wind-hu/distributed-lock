/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.redisson.impls;

import com.windhu.distributed.lock.abstracts.AbstractDistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <p>Redisson实现的分布式锁</p>
 *
 * @author : wind-hu
 **/
@Slf4j
@Order(1)
@RequiredArgsConstructor
public class RedissonDistributedLock extends AbstractDistributedLock<RLock> {
    private final RedissonClient redissonClient;

    /**
     * 加锁
     *
     * @param lockKey    锁key，需要传
     * @param lockValue  可不传
     * @param expireTime 过期时间
     * @return 加锁对象
     */
    @Override
    public RLock acquire(String lockKey, String lockValue, long expireTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean isLock = lock.tryLock(expireTime, TimeUnit.MILLISECONDS);
            return isLock ? lock : null;
        } catch (InterruptedException e) {
            log.error("RedissonDistributedLock tryLock failed.", e);
            return null;
        }
    }

    /**
     * 解锁
     *
     * @param lockKey    可不传
     * @param lockValue  可不传
     * @param lockObject 需要传,加锁的对象
     * @return 是否解锁成功
     */
    @Override
    public boolean release(String lockKey, String lockValue, RLock lockObject) {
        if (lockObject.isHeldByCurrentThread()) {
            try {
                return lockObject.forceUnlockAsync().get();
            } catch (InterruptedException | ExecutionException e) {
                return false;
            }
        }
        return false;
    }
}
