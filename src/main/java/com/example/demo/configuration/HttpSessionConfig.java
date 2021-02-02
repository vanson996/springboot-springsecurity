package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * @author WYX
 * @date 2021/1/27
 */
// 启用基于 redis 的httpSession实现
@EnableRedisHttpSession
public class HttpSessionConfig {

    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    /**
     * SpringSessionBackedSessionRegistry 是 session 为 spring security 提供的
     * 用于在集群环境下控制会话并发的会话注册表实现类
     */
    @Bean
    public SpringSessionBackedSessionRegistry springSessionBackedSessionRegistry() {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    /**
     * httpsession 的事件监听，改用session提供的会话注册表
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String s="blurooo";
        String s1 = encoder.encode(s);
        System.out.println(s1);
    }
}
