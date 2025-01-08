package com.spring.service;

import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import com.spring.dto.StockDetailsDTO;
import com.spring.entity.Stock;
import com.spring.repository.StockRepository;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public StockDetailsDTO getStockDetails(Long stockId) throws Exception {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ExpressionException("Stock not found"));

        return mapToDTO(stock);
    }

    private StockDetailsDTO mapToDTO(Stock stock) {
        StockDetailsDTO dto = new StockDetailsDTO();
        dto.setId(stock.getId());
        dto.setStockName(stock.getStockName());
        dto.setStockSymbol(stock.getStockSymbol());
        dto.setOpenPrice(stock.getOpenPrice());
        dto.setClosePrice(stock.getClosePrice());
        dto.setHighPrice(stock.getHighPrice());
        dto.setLowPrice(stock.getLowPrice());
        dto.setSettlementPrice(stock.getSettlementPrice());
        return dto;
    }
}
