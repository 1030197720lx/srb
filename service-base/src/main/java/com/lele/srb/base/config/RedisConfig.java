package com.lele.srb.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        //设置连接池工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //先解决key的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);

        //value序列化
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer  =new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
        //解决jsckson2无法反序列化loaclDateTime的问题
        //禁用原有的日期序列化方式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //使用新的序列化方案
        objectMapper.registerModule(new JavaTimeModule());
        jsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

}
