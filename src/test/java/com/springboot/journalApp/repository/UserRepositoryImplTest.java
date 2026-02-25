package com.springboot.journalApp.repository;

import com.springboot.journalApp.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void testGetUsersForSentimentAnalysis() {

        List<Users> users = userRepositoryImpl.getUsersForSentimentAnalysis();
    }
}
