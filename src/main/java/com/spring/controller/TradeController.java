package com.spring.controller;

import com.spring.producer.TradeProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.dto.TradeRequestDTO;
import com.spring.service.impl.TradeServiceImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/trade")
public class TradeController {

    private final TradeProducer tradeProducer;
    private final TradeServiceImpl tradeServiceImpl;

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
    @PostMapping("/close-market")
    public ResponseEntity<?> closeMarket() {
        try {
            tradeServiceImpl.closeMarket();
            return ResponseEntity.ok().body(
                    "{ \"status\": \"Success\", \"message\": \"" + "Market closed successfully " + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "{ \"status\": \"Failure\", \"message\": \"" + e.getMessage() + "\"}"
            );
        }
    }

}
