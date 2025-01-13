package com.spring.util;

import com.spring.entity.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVParserUtilTest {

    private CSVParserUtil csvParserUtil;

    @BeforeEach
    void setUp() {
        csvParserUtil = new CSVParserUtil();
    }

    @Test
    void testParseCsvToStocks_ValidFile() throws Exception {
        // Arrange
        String csvContent = """
            MARKET,SERIES,SYMBOL,SECURITY,PREV_CL_PR,OPEN_PRICE,HIGH_PRICE,LOW_PRICE,CLOSE_PRICE,NET_TRDVAL,NET_TRDQTY,CORP_IND,HI_52_WK,LO_52_WK
            N,SM,AATMAJ,AATMAJ HEALTHCARE LIMITED,24.50,24.50,24.65,24.50,24.65,98300.00,4000, ,43.10,23.10
            N,SM,ABSMARINE,ABS MARINE SERVICES LTD,193.85,193.90,196.85,190.00,190.50,7416400.00,38500, ,425.00,183.00
            """;
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "stocks.csv", "text/csv", csvContent.getBytes()
        );

        // Act
        List<Stock> stocks = csvParserUtil.parseCsvToStocks(mockFile);

        // Assert
        assertNotNull(stocks);
        assertEquals(2, stocks.size());

        Stock firstStock = stocks.get(0);
        assertEquals("AATMAJ", firstStock.getStockSymbol());
        assertEquals("AATMAJ HEALTHCARE LIMITED", firstStock.getStockName());
        assertEquals(24.50, firstStock.getPrevClose());
        assertEquals(24.50, firstStock.getOpenPrice());
        assertEquals(24.65, firstStock.getHighPrice());
        assertEquals(24.50, firstStock.getLowPrice());
        assertEquals(43.10, firstStock.getHi52Week());
        assertEquals(23.10, firstStock.getLo52Week());
    }

    @Test
    void testParseCsvToStocks_EmptyFile() throws Exception {
        // Arrange
        String csvContent = "MARKET,SERIES,SYMBOL,SECURITY,PREV_CL_PR,OPEN_PRICE,HIGH_PRICE,LOW_PRICE,CLOSE_PRICE,NET_TRDVAL,NET_TRDQTY,CORP_IND,HI_52_WK,LO_52_WK\n";
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "empty.csv", "text/csv", csvContent.getBytes()
        );

        // Act
        List<Stock> stocks = csvParserUtil.parseCsvToStocks(mockFile);

        // Assert
        assertNotNull(stocks);
        assertTrue(stocks.isEmpty());
    }

    @Test
    void testParseCsvToStocks_MalformedData() {
        // Arrange
        String csvContent = """
            MARKET,SERIES,SYMBOL,SECURITY,PREV_CL_PR,OPEN_PRICE,HIGH_PRICE,LOW_PRICE,CLOSE_PRICE,NET_TRDVAL,NET_TRDQTY,CORP_IND,HI_52_WK,LO_52_WK
            N,SM,AATMAJ,AATMAJ HEALTHCARE LIMITED,INVALID,24.50,24.65,24.50,24.65,98300.00,4000, ,43.10,23.10
            """;
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "malformed.csv", "text/csv", csvContent.getBytes()
        );

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> csvParserUtil.parseCsvToStocks(mockFile));
        assertTrue(exception.getMessage().contains("For input string: \"INVALID\""));
    }
}
