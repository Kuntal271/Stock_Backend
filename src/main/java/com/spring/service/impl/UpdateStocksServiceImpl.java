package com.spring.service.impl;

import com.spring.service.UpdateStocksService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.entity.Stock;
import com.spring.repository.StockRepository;
import com.spring.util.CSVParserUtil;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateStocksServiceImpl implements UpdateStocksService {

    private final StockRepository stockRepository;
    private final CSVParserUtil csvParserUtil;

    @Override
    public void updateStocksFromCSV(MultipartFile file) throws Exception {
        List<Stock> stocks = csvParserUtil.parseCsvToStocks(file);
        stockRepository.saveAll(stocks);
    }
}
