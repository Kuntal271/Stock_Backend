package com.spring.controller;

import com.spring.producer.TradeProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.dto.TradeRequestDTO;
import com.spring.service.TradeService;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    private final TradeProducer tradeProducer;

    public TradeController(TradeProducer tradeProducer) {
        this.tradeProducer = tradeProducer;
    }

    /**
     * Trade API (BUY/SELL)
     * POST /api/trade
     */
    @PostMapping
    public ResponseEntity<?> executeTrade(@RequestBody TradeRequestDTO tradeRequest) {
        try {
            tradeProducer.sendTradeRequest(tradeRequest);
            return ResponseEntity.ok().body(
                    "{ \"status\": \"Success\", \"message\": \"" + "Trade successful " + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "{ \"status\": \"Failure\", \"message\": \"" + e.getMessage() + "\"}"
            );
        }
    }
}
