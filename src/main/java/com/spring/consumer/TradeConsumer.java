package com.spring.consumer;

import com.spring.dto.TradeRequestDTO;
import com.spring.service.impl.TradeServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TradeConsumer {

    private final TradeServiceImpl tradeService;

    public TradeConsumer(TradeServiceImpl tradeServiceImpl) {
        this.tradeService = tradeServiceImpl;
    }

    @KafkaListener(topics = "new-trade", groupId = "trade-group")
    public void consumeTrade(TradeRequestDTO tradeRequest){
        try {
            tradeService.executeTrade(tradeRequest);
        } catch (Exception e) {
            System.err.println("Error processing trade: " + e.getMessage());
        }
    }
}
