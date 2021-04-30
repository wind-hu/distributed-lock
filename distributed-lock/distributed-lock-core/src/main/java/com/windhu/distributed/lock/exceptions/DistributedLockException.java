/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.exceptions;

/**
 * <p>分布式锁异常类</p>
 *
 * @author : wind-hu
 **/
public class DistributedLockException extends RuntimeException {
    public DistributedLockException() {
        super();
    }

    public DistributedLockException(String message) {
        super(message);
    }
}
