package com.springboot.journalApp.repository;

import com.springboot.journalApp.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Users> getUsersForSentimentAnalysis() {

        Query query = new Query();
        query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentAnalysisEnabled").is(true));
        List<Users> users = mongoTemplate.find(query, Users.class);
        return users;
    }
}
