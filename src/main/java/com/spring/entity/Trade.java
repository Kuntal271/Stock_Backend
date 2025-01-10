package com.spring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private Long stockId;
    private String tradeType;  // “BUY” or “SELL”
    private Integer quantity;

    // For simplicity, we'll store the buy/sell price as well
    private Double priceAtTradeTime;
}
