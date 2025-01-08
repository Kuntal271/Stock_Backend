package com.spring.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResponseDTO {
    // For each holding
    private Long stockId;
    private String stockName;
    private Integer quantity;
    private Double buyPrice;      // Weighted average buy price
    private Double currentPrice;
    private Double gainLoss;      // currentPrice - buyPrice
    private Double gainLossPercent;
}
