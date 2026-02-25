package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.Users;
import com.springboot.journalApp.service.JournalEntryService;
import com.springboot.journalApp.service.UsersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UsersService usersService;

//    @GetMapping("/getEntries/{username}")
//    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
//        Users users = usersService.findingByUsername(username);
//        List<JournalEntry> all = users.getJournalEntries();
//        if(!all.isEmpty() && all != null){
//            return new ResponseEntity<>(all, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping("/createEntry/{username}")
//    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username) {
//        try {
//            journalEntryService.saveJournalEntry(myEntry, username);
//            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/getEntryById/{myId}")
//    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {
//        Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
//        if (journalEntry.isPresent()) {
//            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//        }
//        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @DeleteMapping("/deleteEntry/{username}/{id}")
//    public ResponseEntity<?> deleteEntry(@RequestParam ObjectId id, @PathVariable String username){
//        journalEntryService.deleteById(id, username);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("/updateEntryById/{username}/{myId}")
//    public JournalEntry updateEntryById(@PathVariable ObjectId myId,
//                                        @RequestBody JournalEntry updatedEntry,
//                                        @PathVariable String username) {
////        Users user = usersService.findingByUsername(username);
//        JournalEntry old = journalEntryService.getById(myId).orElse(null);
//        if(old != null) {
//            old.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : old.getTitle());
//            old.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : old.getContent());
//        }
//        journalEntryService.saveJournalEntry(old);
//        return old;
//    }

    @GetMapping("/getEntries")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = usersService.findingByUsername(username);
        List<JournalEntry> all = users.getJournalEntries();
        if(!all.isEmpty() && all != null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveJournalEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getEntryById/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = usersService.findingByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteEntry/{id}")
    public ResponseEntity<?> deleteEntry(@RequestParam ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteById(id, username);
        if(removed)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateEntryById/{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId myId,
                                        @RequestBody JournalEntry updatedEntry) {
//        Users user = usersService.findingByUsername(username);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = usersService.findingByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){

            Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
            if(journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : old.getTitle());
                old.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : old.getContent());
                journalEntryService.saveJournalEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
