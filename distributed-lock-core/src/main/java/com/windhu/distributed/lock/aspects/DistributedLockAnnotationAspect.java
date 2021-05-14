/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.aspects;

import com.windhu.distributed.lock.annotation.DistributedLock;
import com.windhu.distributed.lock.autoconfigure.properties.DistributedLockProperties;
import com.windhu.distributed.lock.model.DistributedLockInfo;
import com.windhu.distributed.lock.templates.DistributedLockTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * <p>注解切面</p>
 *
 * @author : wind-hu
 **/
@Aspect
@EnableAspectJAutoProxy
public class DistributedLockAnnotationAspect implements Ordered {
    private DistributedLockTemplate distributedLockTemplate;

    private DistributedLockProperties distributedLockProperties;

    public DistributedLockAnnotationAspect(DistributedLockTemplate template, DistributedLockProperties properties) {
        this.distributedLockTemplate = template;
        this.distributedLockProperties = properties;
    }

    @Pointcut("@annotation(com.windhu.distributed.lock.annotation.DistributedLock)")
    public void distributedLockAnnotationPointCut() {
    }

    @Around(value = "distributedLockAnnotationPointCut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        DistributedLockInfo lock = null;
        if (annotation != null) {
            lock = distributedLockTemplate.lock(buildKey(method), annotation.expire());
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            if (annotation != null && lock != null) {
                distributedLockTemplate.unlock(lock);
            }
        }
    }

    /**
     * 若注解中未配置了key，使用类名加方法名
     *
     * @param method 方法对象
     * @return key
     */
    private String buildKey(Method method) {
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        if (StringUtils.isEmpty(annotation.key())) {
            Class<?> declaringClass = method.getDeclaringClass();
            return declaringClass.getName() + "." + method.getName();
        }
        return annotation.key();
    }

    @Override
    public int getOrder() {
        return distributedLockProperties.getAspectOrder();
    }
}
