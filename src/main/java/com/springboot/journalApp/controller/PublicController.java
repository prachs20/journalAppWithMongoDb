package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.Users;
import com.springboot.journalApp.service.UsersService;
import com.springboot.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/healthMapping")
    public String healthCheck() {
        return "Okay";
    }

    @PostMapping("/signUp")
    public ResponseEntity<Users> signUp(@RequestBody Users user) {
        try {
            usersService.saveNewUsers(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        try {
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return new ResponseEntity<>(jwt, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Login failed for user: " + user.getUsername(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
