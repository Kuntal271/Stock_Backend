package com.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.service.impl.UpdateStocksServiceImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stocks")
public class UpdateStocksController {

    private final UpdateStocksServiceImpl updateStocksServiceImpl;

    /**
     * Housekeeping API to update stocks data from CSV
     * POST /api/stocks/update
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateStocks(@RequestParam("file") MultipartFile file) {
        try {
            updateStocksServiceImpl.updateStocksFromCSV(file);
            return ResponseEntity.ok("{\"message\": \"Stocks updated successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"error\": \"" + e.getMessage() + "\"}"
            );
        }
    }
}
