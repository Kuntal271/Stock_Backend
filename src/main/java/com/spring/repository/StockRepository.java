package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    // Additional query methods if needed
}
