package com.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.dto.StockDetailsDTO;
import com.spring.service.impl.StockServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockServiceImpl stockServiceImpl;


    /**
     * Details API:
     * GET /api/stock/{stockId}
     */
    @GetMapping("/{stockId}")
    public ResponseEntity<?> getStockDetails(@PathVariable("stockId") Long stockId) {
        try {
            StockDetailsDTO dto = stockServiceImpl.getStockDetails(stockId);
            return ResponseEntity.ok(dto);//cacheControl()
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "{ \"status\": \"Failure\", \"message\": \"" + e.getMessage() + "\"}"
            );
        }
    }
    /**
     * Get All Stocks API:
     * GET /api/stock
     */
    @GetMapping
    public ResponseEntity<List<StockDetailsDTO>> getAllStocks() {
        try {
            List<StockDetailsDTO> stocks = stockServiceImpl.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    /**
     * Search API:
     * GET /api/stock/search?stockName=XYZ
     */
    @GetMapping("/search")
    public ResponseEntity<List<StockDetailsDTO>> searchByName(@RequestParam("stockName") String stockName) {
        try {
            List<StockDetailsDTO> stocks = stockServiceImpl.searchByName(stockName);
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
