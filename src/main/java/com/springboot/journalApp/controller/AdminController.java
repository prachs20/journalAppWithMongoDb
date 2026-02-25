package com.springboot.journalApp.controller;

import com.springboot.journalApp.cache.AppCache;
import com.springboot.journalApp.entity.Users;
import com.springboot.journalApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<Users> all = usersService.getAll();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createAdminUser")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        try {
            usersService.saveAdmin(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("clearCache")
    public void clearCache() {
        appCache.init();
    }

}
