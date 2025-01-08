package com.spring.dto;

import lombok.Data;

@Data
public class StockDetailsDTO {
    Long id;
    String stockSymbol;
    String stockName;
    Double openPrice;
    Double settlementPrice;
    Double highPrice;
    Double lowPrice;
}
