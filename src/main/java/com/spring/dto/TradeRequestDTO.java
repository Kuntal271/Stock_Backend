package com.spring.dto;

import com.spring.entity.TradeType;
import lombok.Data;

@Data
public class TradeRequestDTO {
    private String userName;
    private TradeType tradeType;  // “BUY” or “SELL”
    private Integer quantity;
    private Long stockId;
    private Double priceAtTradeTime;
}
