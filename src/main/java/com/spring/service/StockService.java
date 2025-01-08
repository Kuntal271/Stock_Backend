package com.spring.service;

import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import com.spring.dto.StockDetailsDTO;
import com.spring.entity.Stock;
import com.spring.repository.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<StockDetailsDTO> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();

        if (stocks.isEmpty()) {
            throw new ExpressionException("No stocks found");
        }

        return stocks.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private StockDetailsDTO mapToDTO(Stock stock) {
        StockDetailsDTO dto = new StockDetailsDTO();
        dto.setId(stock.getId());
        dto.setStockName(stock.getStockName());
        dto.setStockSymbol(stock.getStockSymbol());
        dto.setOpenPrice(stock.getOpenPrice());
        dto.setSettlementPrice(stock.getSettlementPrice());
        dto.setHighPrice(stock.getHighPrice());
        dto.setLowPrice(stock.getLowPrice());
        dto.setSettlementPrice(stock.getSettlementPrice());
        return dto;
    }
}
