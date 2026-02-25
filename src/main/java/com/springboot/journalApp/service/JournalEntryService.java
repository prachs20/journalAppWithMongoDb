package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.Users;
import com.springboot.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UsersService usersService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveJournalEntry(JournalEntry journalEntry, String username) {
        Users user = usersService.findingByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        usersService.saveUsers(user);
    }

    public void saveJournalEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username) {
        boolean removed = false;
        try {
            Users user = usersService.findingByUsername(username);
            removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if (removed) {
                usersService.saveNewUsers(user);
                journalEntryRepository.deleteById(id);
            }

        } catch (Exception e) {
            logger.info("An error occurred while deleting entry with id: " + id, e);
            throw new RuntimeException("AN error occured while deleting entry", e);
        }
        return removed;
    }
}
