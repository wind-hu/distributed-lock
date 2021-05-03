/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.autoconfigure.properties;

import com.windhu.distributed.lock.interfaces.IDistributedLock;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>分布式锁配置</p>
 *
 * @author : wind-hu
 **/
@Data
@ConfigurationProperties(prefix = "distributed.lock")
public class DistributedLockProperties {
    /**
     * 过期时间: 30s（default）
     * 单位毫秒
     */
    private long expireTime = 30000L;

    /**
     * 重试间隔时间: 1s（default）
     * 单位:毫秒
     */
    private long retryInterval = 1000L;

    /**
     * 重试次数
     */
    private int retryCount = 3;

    /**
     * 分布式锁实现类（redisson、redisTemplate、zookeeper）
     */
    private Class<? extends IDistributedLock> distributedLockImplement;
}
