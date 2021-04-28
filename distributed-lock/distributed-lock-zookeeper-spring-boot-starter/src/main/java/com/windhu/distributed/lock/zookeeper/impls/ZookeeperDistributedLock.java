/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.zookeeper.impls;

import com.windhu.distributed.lock.abstracts.AbstractDistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

/**
 * <p>zookeeper分布式锁</p>
 *
 * @author : wind-hu
 **/
@Slf4j
@Order(99)
@RequiredArgsConstructor
public class ZookeeperDistributedLock extends AbstractDistributedLock {
    private final CuratorFramework curatorFramework;

    @Override
    public Object acquire(String lockKey, String lockValue, long expireTime) {
        return null;
    }

    @Override
    public boolean release(String lockKey, String lockValue, Object lockObject) {
        return false;
    }
}
