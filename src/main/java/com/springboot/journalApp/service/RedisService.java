package com.springboot.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T getKeyFromRedis(String key, Class<T> genericClass){
        try {
            Object value = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(value.toString(), genericClass);
        }
        catch (Exception e){
            log.error("Exception:",e);
            return null;
        }
    }

    public void setKeyInRedis(String key, Object o, Long expiryTimeInSeconds){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String JsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, JsonValue, expiryTimeInSeconds);
        }
        catch (Exception e){
            log.error("Exception:",e);
        }
    }
}
