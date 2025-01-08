package com.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.TradeRequestDTO;
import com.spring.entity.Stock;
import com.spring.entity.Trade;
import com.spring.entity.UserHolding;
import com.spring.repository.StockRepository;
import com.spring.repository.TradeRepository;
import com.spring.repository.UserHoldingRepository;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final StockRepository stockRepository;
    private final UserHoldingRepository userHoldingRepository;

    public TradeService(TradeRepository tradeRepository,
                        StockRepository stockRepository,
                        UserHoldingRepository userHoldingRepository) {
        this.tradeRepository = tradeRepository;
        this.stockRepository = stockRepository;
        this.userHoldingRepository = userHoldingRepository;
    }

    @Transactional
    public String executeTrade(TradeRequestDTO request) throws Exception, Exception {
        // Validate stock
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new Exception("Stock not found"));

        // Validate quantity
        if (request.getQuantity() <= 0) {
            throw new Exception("Quantity must be greater than zero.");
        }

        // Compute price at trade time if not provided (or fallback to closePrice)
        Double tradePrice = request.getPriceAtTradeTime() == null
                ? stock.getClosePrice() : request.getPriceAtTradeTime();

        // Create trade record
        Trade trade = Trade.builder()
                .userAccountId(request.getUserAccountId())
                .stockId(stock.getId())
                .tradeType(request.getTradeType().toUpperCase())
                .quantity(request.getQuantity())
                .priceAtTradeTime(tradePrice)
                .build();
        tradeRepository.save(trade);

        // Update user holdings
        UserHolding holding = userHoldingRepository
                .findByUserIdAndStockId(request.getUserAccountId(), stock.getId());

        if ("BUY".equalsIgnoreCase(request.getTradeType())) {
            if (holding == null) {
                // Create new holding
                holding = UserHolding.builder()
                        .userId(request.getUserAccountId())
                        .stockId(stock.getId())
                        .quantity(request.getQuantity())
                        .averageBuyPrice(tradePrice)
                        .build();
            } else {
                // Update existing holding
                Integer totalQuantity = holding.getQuantity() + request.getQuantity();
                // Weighted average
                double totalCost = holding.getAverageBuyPrice() * holding.getQuantity()
                        + tradePrice * request.getQuantity();
                double newAvgPrice = totalCost / totalQuantity;

                holding.setQuantity(totalQuantity);
                holding.setAverageBuyPrice(newAvgPrice);
            }
            userHoldingRepository.save(holding);

        } else if ("SELL".equalsIgnoreCase(request.getTradeType())) {
            if (holding == null || holding.getQuantity() < request.getQuantity()) {
                throw new Exception("Insufficient quantity to sell");
            }
            // Decrease quantity
            Integer updatedQuantity = holding.getQuantity() - request.getQuantity();
            holding.setQuantity(updatedQuantity);

            // If quantity becomes 0, you can choose to remove the record or keep with zero quantity
            if (updatedQuantity == 0) {
                holding.setAverageBuyPrice(0.0);
            }
            userHoldingRepository.save(holding);
        } else {
            throw new Exception("Invalid trade type. Allowed values: BUY or SELL");
        }

        return "Trade executed successfully";
    }
}
