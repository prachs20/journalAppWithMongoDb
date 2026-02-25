package com.springboot.journalApp.scheduler;

import com.springboot.journalApp.entity.JournalEntry;
import com.springboot.journalApp.entity.Users;
import com.springboot.journalApp.repository.UserRepositoryImpl;
import com.springboot.journalApp.service.EmailService;
import com.springboot.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl usersRepository;

    @Scheduled(cron = "0 0 9 * * SUN") // Every Sunday at 9 AM
    public void fetchUsersAndSendSentimentAnalysisEmail() {
        List<Users> usersForSentimentAnalysis = usersRepository.getUsersForSentimentAnalysis();
        for(Users user : usersForSentimentAnalysis){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x-> x.getContent())
                    .collect(Collectors.toList());
            String entry = String.join(" ",filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Your Weekly Sentiment Analysis", "Your sentiment for the past week is: " + sentiment);
            }
        }

    }

