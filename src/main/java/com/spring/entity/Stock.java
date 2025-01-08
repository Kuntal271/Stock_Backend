package com.spring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockSymbol;        // e.g. “TCS”
    private String stockName;          // e.g. “Tata Consultancy Services”

    // Basic pricing info
    private Double prevClose;
    private Double openPrice;
    private Double closePrice;
    private Double highPrice;
    private Double lowPrice;
    private Double hi52Week;
    private Double lo52Week;
    private Double settlementPrice;
}
