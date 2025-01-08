package com.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.entity.Stock;
import com.spring.repository.StockRepository;
import com.spring.util.CSVParserUtil;
import java.util.List;

@Service
public class UpdateStocksService {

    private final StockRepository stockRepository;
    private final CSVParserUtil csvParserUtil;

    public UpdateStocksService(StockRepository stockRepository,
                               CSVParserUtil csvParserUtil) {
        this.stockRepository = stockRepository;
        this.csvParserUtil = csvParserUtil;
    }

    public void updateStocksFromCSV(MultipartFile file) throws Exception {
        List<Stock> stocks = csvParserUtil.parseCsvToStocks(file);
        stockRepository.saveAll(stocks);
    }
}
