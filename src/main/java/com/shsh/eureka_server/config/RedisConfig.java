package com.shsh.eureka_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {


//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory();
//    }
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        String redisHost = "redis";
        int redisPort = 6379;

        System.out.println("Connecting to Redis at " + redisHost + ":" + redisPort);
        return new LettuceConnectionFactory(redisHost, redisPort);
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
