package com.spring.service.impl;

import com.spring.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import com.spring.dto.StockDetailsDTO;
import com.spring.entity.Stock;
import com.spring.repository.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public StockDetailsDTO getStockDetails(Long stockId) throws Exception {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ExpressionException("Stock not found"));

        return mapToDTO(stock);
    }
    @Override
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
       dto.setCurrentPrice(stock.getCurrentPrice());
       dto.setLowPrice(stock.getLowPrice());
       dto.setHighPrice(stock.getHighPrice());
       return dto;
    }

    @Override
    public List<StockDetailsDTO> searchByName(String stockName){
        List<Stock> stocks = stockRepository.searchByNameFuzzy(stockName);
        return stocks.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
