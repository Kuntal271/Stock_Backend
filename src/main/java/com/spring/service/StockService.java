package com.spring.service;

import com.spring.dto.StockDetailsDTO;
import com.spring.entity.Stock;

import java.util.List;

public interface StockService {

    StockDetailsDTO getStockDetails(Long stockId) throws Exception;

    List<StockDetailsDTO> getAllStocks();
    List<StockDetailsDTO> searchByName(String stockName);
}
