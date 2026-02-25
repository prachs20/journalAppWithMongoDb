package com.springboot.journalApp.controller;

import com.springboot.journalApp.apiResponse.WeatherResponse;
import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.Users;
import com.springboot.journalApp.repository.UsersRepository;
import com.springboot.journalApp.service.UsersService;
import com.springboot.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WeatherService weatherService;

//    @GetMapping("/getUsers")
//    public List<Users> getAllUsers() {
//        return usersService.getAll();
//    }

//    @PostMapping("/createUser")
//    public ResponseEntity<Users> createUser(@RequestBody Users user) {
//        try {
//            usersService.saveUsers(user);
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    @GetMapping("/getUserById/{myId}")
//    public ResponseEntity<Users> getUserById(@PathVariable ObjectId myId) {
//        Optional<Users> user = usersService.getById(myId);
//        if (user.isPresent()) {
//            return new ResponseEntity<>(user.get(), HttpStatus.OK);
//        }
//        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @DeleteMapping("/deleteUser")
//    public String deleteUser(@RequestParam ObjectId id){
//        usersService.deleteById(id);
//        return "removed id";
//    }

//    @PutMapping("/updateUser/{username}")
//    public Users updateUser(@RequestBody Users updatedUser, @PathVariable String username) {
//        Users userInDb = usersService.findingByUsername(username);
//        if(userInDb != null) {
//            userInDb.setPassword(updatedUser.getPassword());
//            userInDb.setUsername(updatedUser.getUsername());
//        }
//        usersService.saveUsers(userInDb);
//        return userInDb;
//    }

    @PutMapping("/updateUser")
    public Users updateUser(@RequestBody Users updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users userInDb = usersService.findingByUsername(username);
            userInDb.setPassword(updatedUser.getPassword());
            userInDb.setUsername(updatedUser.getUsername());
        usersService.saveNewUsers(userInDb);
        return userInDb;
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        usersRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("greeting")
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(weatherResponse != null) {
            greeting = "Hi " + username + ", Weather feels like " + weatherResponse.getCurrent().getTemperature();

        }
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }

}
