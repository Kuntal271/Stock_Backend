package com.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.service.UpdateStocksService;

@RestController
@RequestMapping("/api/stocks")
public class UpdateStocksController {

    private final UpdateStocksService updateStocksService;

    public UpdateStocksController(UpdateStocksService updateStocksService) {
        this.updateStocksService = updateStocksService;
    }

    /**
     * Housekeeping API to update stocks data from CSV
     * POST /api/stocks/update
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateStocks(@RequestParam("file") MultipartFile file) {
        try {
            updateStocksService.updateStocksFromCSV(file);
            return ResponseEntity.ok("{\"message\": \"Stocks updated successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"error\": \"" + e.getMessage() + "\"}"
            );
        }
    }
}
