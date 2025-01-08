package com.spring.controller;

import org.springframework.http.ResponseEntity;

import com.spring.dto.PortfolioResponseDTO;
import com.spring.service.PortfolioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    /**
     * Portfolio API:
     * GET /api/portfolio?userId=XYZ
     */
    @GetMapping
    public ResponseEntity<?> getPortfolio(@RequestParam("userId") Long userId) {
        List<PortfolioResponseDTO> holdings = portfolioService.getUserPortfolio(userId);

        // Calculate total portfolio value, total buy price, total P/L, etc.
        double totalBuyPrice = holdings.stream()
                .mapToDouble(h -> h.getBuyPrice() * h.getQuantity())
                .sum();

        double totalCurrentValue = holdings.stream()
                .mapToDouble(h -> h.getCurrentPrice() * h.getQuantity())
                .sum();

        double totalPL = totalCurrentValue - totalBuyPrice;
        double totalPLPercent = (totalBuyPrice == 0.0) ? 0.0 : (totalPL / totalBuyPrice * 100);

        Map<String, Object> response = new HashMap<>();
        response.put("holdings", holdings);
        response.put("totalPortfolioValue", totalCurrentValue);
        response.put("totalBuyPrice", totalBuyPrice);
        response.put("totalPL", totalPL);
        response.put("totalPLPercent", totalPLPercent);

        return ResponseEntity.ok(response);
    }
}
