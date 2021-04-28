/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.exceptions;

/**
 * <p>分布式锁异常类</p>
 *
 * @author : wind-hu
 **/
public class DistributedLockExpection extends RuntimeException {
    public DistributedLockExpection() {
        super();
    }

    public DistributedLockExpection(String message) {
        super(message);
    }
}
