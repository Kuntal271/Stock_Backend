package com.spring.dto;

import lombok.Data;

@Data
public class TradeRequestDTO {
    private String userName;
    private String tradeType;  // “BUY” or “SELL”
    private Integer quantity;
    private Long stockId;
    private Double priceAtTradeTime;
}
