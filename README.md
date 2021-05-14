# distributed-lock
分布式锁starter
整合了redis、redisson、zookeeper实现的分布式锁的自动配置

# implements
✔ redis   
✔ redisson  
❌ zookeeper   
zookeeper部分实现会后续补充上去

# usage instruction
1. 引入starter
此依赖包含redis、redisson、zookeeper的starter
```xml
<dependency>
    <groupId>com.windhu</groupId>
    <artifactId>distributed-lock-spring-boot-starter</artifactId>
    <version>1.0</version>
</dependency>
```

2. 配置中间件连接信息
  配置redis、zookeeper的连接信息

3. 配置分布式锁具体实现
  根据需要选择实现类
  根据业务场景可灵活配置 过期时间、重试次数、重试间隔时间、注解处理用切面的order数值
```yaml
distributed:
  lock:
    distributed-lock-implement: com.windhu.distributed.lock.redisson.impls.RedissonDistributedLock
    expireTime: 30000
    retryInterval: 1000
    retryCount: 3
    aspect-order: 1
```
