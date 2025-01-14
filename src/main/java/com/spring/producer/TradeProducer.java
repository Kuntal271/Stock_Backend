package com.spring.producer;

import com.spring.dto.TradeRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TradeProducer {
    private final KafkaTemplate<String, TradeRequestDTO> kafkaTemplate;

    public TradeProducer(KafkaTemplate<String, TradeRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTradeRequest(TradeRequestDTO tradeRequest) {
        kafkaTemplate.send("new-trade", tradeRequest);
    }
}
