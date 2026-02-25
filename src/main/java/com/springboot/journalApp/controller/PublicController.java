package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.Users;
import com.springboot.journalApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/healthMapping")
    public String healthCheck() {
        return "Okay";
    }

    @PostMapping("/createUser")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        try {
            usersService.saveNewUsers(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
