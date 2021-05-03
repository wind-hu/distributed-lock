/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.model;

import com.windhu.distributed.lock.interfaces.IDistributedLock;
import lombok.Builder;
import lombok.Data;

/**
 * <p>分布式锁执行信息</p>
 *
 * @author : wind-hu
 **/
@Data
@Builder
public class DistributedLockInfo {
    /**
     * 锁key
     */
    private String lockKey;

    /**
     * 锁value
     */
    private String lockValue;

    /**
     * 过期时间
     */
    private long expireTime;

    /**
     * 重试次数
     */
    private int retryCount;

    /**
     * 重试间隔
     */
    private long retryInterval;

    /**
     * 分布式锁对象
     */
    private Object distributedLockObject;

    /**
     * 分布式锁实例
     */
    private IDistributedLock distributedLockImplement;
}
