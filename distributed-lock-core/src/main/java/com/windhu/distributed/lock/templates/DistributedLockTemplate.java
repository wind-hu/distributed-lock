/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.templates;

import com.windhu.distributed.lock.autoconfigure.properties.DistributedLockProperties;
import com.windhu.distributed.lock.exceptions.DistributedLockException;
import com.windhu.distributed.lock.interfaces.IDistributedLock;
import com.windhu.distributed.lock.model.DistributedLockInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>分布式锁模板方法</p>
 *
 * @author : wind-hu
 **/
@Slf4j
public class DistributedLockTemplate {
    private DistributedLockProperties distributedLockProperties;

    private List<? extends IDistributedLock> distributedLockInstances;

    private IDistributedLock distributedLockImpl;

    public DistributedLockTemplate(DistributedLockProperties distributedLockProperties,
                                   List<? extends IDistributedLock> distributedLockInstances) {
        this.distributedLockProperties = distributedLockProperties;
        this.distributedLockInstances = distributedLockInstances;
    }

    /**
     * 初始化属性
     */
    @PostConstruct
    public void init() {
        Assert.isTrue(distributedLockProperties.getExpireTime() > 0, "ExpireTime must more than zero.");
        Assert.isTrue(distributedLockProperties.getRetryInterval() > 0, "RetryInterval must more than zero.");
        Assert.isTrue(distributedLockProperties.getRetryCount() > 0, "RetryCount must more than zero.");
        Assert.notEmpty(distributedLockInstances, "distributedLock Implements must have at least one.");

        if (null != distributedLockProperties.getDistributedLockImplement()) {
            this.distributedLockImpl = distributedLockInstances.stream()
                    .filter(item -> item.getClass() == distributedLockProperties.getDistributedLockImplement())
                    .collect(Collectors.toList())
                    .get(0);
        } else {
            // @Order control
            distributedLockImpl = distributedLockInstances.get(0);
        }
    }

    /**
     * 加锁，默认过期时间
     *
     * @param key 分布式锁key
     * @return 锁信息
     */
    public DistributedLockInfo lock(String key) {
        return lock(key, 0);
    }

    /**
     * 加锁，自定义过期时间
     *
     * @param key    分布式锁key
     * @param expire 过期时间 单位毫秒
     * @return 锁信息
     */
    public DistributedLockInfo lock(String key, long expire) {
        expire = expire <= 0 ? distributedLockProperties.getExpireTime() : expire;
        String value = String.valueOf(Thread.currentThread().getId());
        int retryCount = 0;
        try {
            while (retryCount < distributedLockProperties.getRetryCount()) {
                Object acquire = distributedLockImpl.acquire(key, value, expire);
                retryCount++;
                if (null != acquire) {
                    return DistributedLockInfo.builder()
                            .lockKey(key)
                            .lockValue(value)
                            .expireTime(expire)
                            .retryCount(retryCount)
                            .distributedLockObject(acquire)
                            .distributedLockImplement(distributedLockImpl)
                            .retryInterval(distributedLockProperties.getRetryInterval())
                            .build();
                }
                Thread.sleep(distributedLockProperties.getRetryInterval());
            }
        } catch (InterruptedException e) {
            log.error("Thread sleep error", e);
            throw new DistributedLockException("Thread sleep error.");
        }
        throw new DistributedLockException("Get DistributedLock time out.");
    }

    /**
     * 解锁
     *
     * @param lockInfo 锁信息
     * @return 是否解锁成功
     */
    public boolean unlock(DistributedLockInfo lockInfo) {
        if (null == lockInfo) {
            return false;
        }
        return lockInfo.getDistributedLockImplement().release(lockInfo.getLockKey(), lockInfo.getLockValue(),
                lockInfo.getDistributedLockObject());
    }
}
