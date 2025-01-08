package com.spring.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.spring.entity.Stock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVParserUtil {

    /**
     * Parses the entire CSV, returning a list of all stocks.
     *
     * @param file the CSV file uploaded via multipart
     * @return a list of Stock objects
     * @throws Exception if there's an error reading or parsing the file
     */
    public List<Stock> parseCsvToStocks(MultipartFile file) throws Exception {
        List<Stock> stockList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // Read and discard the header (optional)
            String headerLine = br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens.length < 14) {
                    continue;  // or throw an exception
                }
                Stock stock = Stock.builder()
                        .stockSymbol(tokens[2])           // SYMBOL
                        .stockName(tokens[3])             // SECURITY
                        .prevClose(Double.parseDouble(tokens[4]))
                        .openPrice(Double.parseDouble(tokens[5]))
                        .highPrice(Double.parseDouble(tokens[6]))
                        .lowPrice(Double.parseDouble(tokens[7]))
                        .settlementPrice(Double.parseDouble(tokens[8]))
                        .hi52Week(Double.parseDouble(tokens[12]))
                        .lo52Week(Double.parseDouble(tokens[13]))
                        .build();

                stockList.add(stock);
            }

            // Log the size of the stock list
            System.out.println("Total Stocks Parsed: " + stockList.size());
        }

        return stockList;
    }
}
