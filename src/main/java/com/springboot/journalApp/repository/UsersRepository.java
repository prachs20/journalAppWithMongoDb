package com.springboot.journalApp.repository;

import com.springboot.journalApp.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Users, ObjectId> {
    Users findByUsername(String username);

    void deleteByUsername(String name);
}

