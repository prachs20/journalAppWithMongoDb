package com.springboot.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionalConfig {
    @Bean
    public PlatformTransactionManager mongoTransactionMethod (MongoDatabaseFactory mongoDatabaseFactory){
        return new MongoTransactionManager(mongoDatabaseFactory);
    }
}
