/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.interfaces;

/**
 * <p>分布式锁接口</p>
 *
 * @author : wind-hu
 **/
public interface IDistributedLock<T> {
    T acquire(String lockKey, String lockValue, long expireTime);

    boolean release(String lockKey, String lockValue, T lockObject);
}
