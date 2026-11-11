package com.ecommerce;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisTestRunner {

    @Bean
    public ApplicationRunner testRedisConnection(StringRedisTemplate stringRedisTemplate) {
        return args -> {
            stringRedisTemplate.opsForValue()
                    .set("ecommerce:redis:test", "Redis Connected");

            String value = stringRedisTemplate.opsForValue().get("ecommerce:redis:test");

            System.out.println("Redis Test Result: " + value);
        };
    }
}
