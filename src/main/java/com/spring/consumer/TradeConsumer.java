package com.spring.consumer;

import com.spring.dto.TradeRequestDTO;
import com.spring.service.TradeService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class TradeConsumer {

    private final TradeService tradeService;

    public TradeConsumer(TradeService tradeService) {
        this.tradeService = tradeService;
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
