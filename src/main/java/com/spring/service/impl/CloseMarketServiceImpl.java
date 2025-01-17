package com.spring.service.impl;

import com.spring.service.CloseMarketService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Closeable;

@Component
public class CloseMarketServiceImpl implements CloseMarketService {

    private final RestTemplate restTemplate;

    public CloseMarketServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    @Scheduled(cron = "0 30 17 * * ?") // 5:30 PM every day
    public void callCloseMarketApi() {
        String closeMarketUrl = "http://localhost:8081/api/trade/close-market";
        try {
            String response = restTemplate.postForObject(closeMarketUrl, null, String.class);
            System.out.println("Market Closure API Response: " + response);
        } catch (Exception e) {
            System.err.println("Error calling Market Closure API: " + e.getMessage());
        }
    }
}
