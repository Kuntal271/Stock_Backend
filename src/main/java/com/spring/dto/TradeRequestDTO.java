package com.spring.dto;

import lombok.Data;

@Data
public class TradeRequestDTO {
    private Long userAccountId;
    private String tradeType;  // “BUY” or “SELL”
    private Integer quantity;
    private Long stockId;
    private Double priceAtTradeTime; // Optional if you want to override
}
