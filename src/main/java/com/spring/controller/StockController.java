package com.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.dto.StockDetailsDTO;
import com.spring.service.StockService;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Details API:
     * GET /api/stock/{stockId}
     */
    @GetMapping("/{stockId}")
    public ResponseEntity<?> getStockDetails(@PathVariable("stockId") Long stockId) {
        try {
            StockDetailsDTO dto = stockService.getStockDetails(stockId);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "{ \"status\": \"Failure\", \"message\": \"" + e.getMessage() + "\"}"
            );
        }
    }
}