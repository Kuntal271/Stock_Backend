package com.spring.dto;

import lombok.Data;

@Data
public class StockDetailsDTO {
    Long id;
    String stockSymbol;
    String stockName;
    Double openPrice;
    Double closePrice;
    Double highPrice;
    Double lowPrice;
    Double settlementPrice;
}
