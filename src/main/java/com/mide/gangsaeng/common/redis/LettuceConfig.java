package com.mide.gangsaeng.common.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.annotation.PreDestroy;

@Configuration
public class LettuceConfig {
    private RedisClient redisClient;
    private StatefulRedisConnection<String, String> connection;

    @Bean
    public RedisClient redisClient() {
        return RedisClient.create(getRedisURI());
    }

    @Bean
    public StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
        return redisClient.connect();
    }

    @Bean
    public RedisCommands<String, String> syncCommands(StatefulRedisConnection<String, String> connection) {
        return connection.sync();
    }

    @PreDestroy
    public void destroy() {
        if (connection != null) {
            connection.close();
        }

        if (redisClient != null) {
            redisClient.shutdown();
        }
    }

    private RedisURI getRedisURI() {
        return RedisURI.create("localhost", 6379);
    }
}
