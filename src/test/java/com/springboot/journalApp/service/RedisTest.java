package com.springboot.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void sendMail(){
        redisTemplate.opsForValue().set("email", "dummy@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
    }
}
