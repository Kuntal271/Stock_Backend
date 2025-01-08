package com.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.dto.TradeRequestDTO;
import com.spring.service.TradeService;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Trade API (BUY/SELL)
     * POST /api/trade
     */
    @PostMapping
    public ResponseEntity<?> executeTrade(@RequestBody TradeRequestDTO tradeRequest) {
        try {
            String result = tradeService.executeTrade(tradeRequest);
            return ResponseEntity.ok().body(
                    "{ \"status\": \"Success\", \"message\": \"" + result + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "{ \"status\": \"Failure\", \"message\": \"" + e.getMessage() + "\"}"
            );
        }
    }
}
