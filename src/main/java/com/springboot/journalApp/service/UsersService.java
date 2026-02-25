package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.Users;
import com.springboot.journalApp.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUsers(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRoles(Arrays.asList("USER"));
        usersRepository.save(users);
    }

    public void saveUsers(Users users) {
        usersRepository.save(users);
    }

    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    public Optional<Users> getById(ObjectId id) {
        return usersRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        usersRepository.deleteById(id);
    }

    public Users findingByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public void saveAdmin(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRoles(Arrays.asList("USER", "ADMIN"));
        usersRepository.save(users);
        log.info("Admin user {} saved successfully", users.getUsername());
    }
}

