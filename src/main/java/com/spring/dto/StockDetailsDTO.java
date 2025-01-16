package com.spring.dto;

import lombok.Data;

@Data
public class StockDetailsDTO {
    Long id;
    String stockSymbol;
    String stockName;
    Double openPrice;
    Double currentPrice;
    Double highPrice;
    Double lowPrice;
}
