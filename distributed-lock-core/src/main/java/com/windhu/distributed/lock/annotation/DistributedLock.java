/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>分布式锁</p>
 *
 * @author : wind-hu
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {
    String key() default "";

    long expire() default 10000L;
}
