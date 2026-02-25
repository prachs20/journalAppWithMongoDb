package com.springboot.journalApp.service;

import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public String getSentiment(String text){
        if(text == null || text.trim().isEmpty()) return "Neutral";
        // Very small heuristic fallback: count positive/negative words.
        String lower = text.toLowerCase();
        String[] positive = new String[]{"good","great","happy","joy","love","excellent","nice","wonderful"};
        String[] negative = new String[]{"bad","sad","angry","hate","terrible","awful","worst","horrible"};
        int score = 0;
        for(String p: positive) if(lower.contains(p)) score++;
        for(String n: negative) if(lower.contains(n)) score--;
        if(score > 0) return "Positive";
        if(score < 0) return "Negative";
        return "Neutral";
    }
}
