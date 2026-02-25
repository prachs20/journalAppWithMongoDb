package com.springboot.journalApp.service;

import com.springboot.journalApp.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindByUsername() {
        assertNotNull(usersRepository.findByUsername("Prachi"));
    }
}
