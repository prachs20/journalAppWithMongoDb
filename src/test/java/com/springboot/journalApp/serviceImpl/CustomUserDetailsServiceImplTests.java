package com.springboot.journalApp.serviceImpl;

import com.springboot.journalApp.entity.Users;
import com.springboot.journalApp.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomUserDetailsServiceImplTests {

    @MockBean//  if we remove @SpringBootTest then we have to use @Mock instead of @MockBean
    private UsersRepository usersRepository;

    @Autowired // and then instead of @Autowired we have to use @InjectMocks and we will also need to initialize using @BeforeEach and MockitoAnnotations.openMocks(this);
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void testLoadUserByUsername() {
        when(usersRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Users.builder().username("Prachi").password("password").roles(new ArrayList<>()).build());
        UserDetails user = customUserDetailsServiceImpl.loadUserByUsername("Prachi");
        assertNotNull(user);
    }
}
