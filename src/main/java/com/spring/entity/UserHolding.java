package com.spring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String userName;
    private Long stockId;
    private Integer quantity;         // Net quantity user is holding
    private Double averageBuyPrice;   // Weighted average of buy price
}
