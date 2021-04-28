/*
 * Copyright (c) 2021-2021. All rights reserved. wind-hu (18861453337@163.com)
 */

package com.windhu.distributed.lock.autoconfigure;

import com.windhu.distributed.lock.autoconfigure.properties.DistributedLockProperties;
import com.windhu.distributed.lock.interfaces.IDistributedLock;
import com.windhu.distributed.lock.templates.DistributedLockTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <p>分布式锁自动配置</p>
 *
 * @author : wind-hu
 **/
@Configuration
@EnableConfigurationProperties(value = DistributedLockProperties.class)
public class DistributedLockAutoConfiguration {
    @Bean
    public DistributedLockTemplate distributedLockTemplate(
            DistributedLockProperties distributedLockProperties, List<IDistributedLock> distributedLocks) {
        DistributedLockTemplate distributedLockTemplate = new DistributedLockTemplate();
        distributedLockTemplate.setDistributedLockProperties(distributedLockProperties);
        distributedLockTemplate.setDistributedLockInstances(distributedLocks);
        return distributedLockTemplate;
    }
}
