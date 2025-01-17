package com.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import com.spring.dto.PortfolioResponseDTO;
import com.spring.service.impl.PortfolioServiceImpl;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioServiceImpl portfolioServiceImpl;

    /**
     * Portfolio API:
     * GET /api/portfolio?userId=XYZ
     */
    @GetMapping
    public ResponseEntity<?> getPortfolio(@RequestParam("userName") String userName) {
        List<PortfolioResponseDTO> holdings = portfolioServiceImpl.getUserPortfolio(userName);

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
