package com.spring.service.impl;

import com.spring.entity.TradeType;
import com.spring.repository.UserRepository;
import com.spring.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.TradeRequestDTO;
import com.spring.entity.Stock;
import com.spring.entity.Trade;
import com.spring.entity.User;
import com.spring.entity.UserHolding;
import com.spring.repository.StockRepository;
import com.spring.repository.TradeRepository;
import com.spring.repository.UserHoldingRepository;

@CommonsLog
@RequiredArgsConstructor
@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final StockRepository stockRepository;
    private final UserHoldingRepository userHoldingRepository;
    private final UserRepository userRepository;



    @Transactional
    @Override
    public void executeTrade(TradeRequestDTO request) throws Exception, Exception {

        // TODO
        // Introduce leverage to execute trade and square off by EOD
        // Validate stock
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new Exception("Stock not found"));

        // Validate quantity
        if (request.getQuantity() <= 0) {
            throw new Exception("Quantity must be greater than zero.");
        }
        if(request.isLeveraged()){
            if(request.getTradeType()==TradeType.SELL){
                UserHolding userHolding =  userHoldingRepository.findByUserNameAndStockId(request.getUserName(), request.getStockId());
                if(userHolding.getQuantity() == 0)
                    throw new Exception("Short selling not possible");

            }
           User user = userRepository.findByUserName(request.getUserName());
           if(user.getTotalBalance() > request.getQuantity() * request.getPriceAtTradeTime())
               throw new Exception("Balance is sufficient to trade set isLeveraged to false");

           if (request.getTradeType() == TradeType.BUY) {
                user.setLeveragedBalance(user.getLeveragedBalance() + request.getQuantity() * request.getPriceAtTradeTime());
           } else {
                user.setLeveragedBalance(user.getLeveragedBalance() - request.getQuantity() * request.getPriceAtTradeTime());
           }
           userRepository.save(user);
        }
        // Compute price at trade time if not provided (or fallback to closePrice)
        Double tradePrice = request.getPriceAtTradeTime() == null
                ? stock.getCurrentPrice() : request.getPriceAtTradeTime();

        // Create trade record
        Trade trade = Trade.builder()
                .userName(request.getUserName())
                .stockId(stock.getId())
                .tradeType(request.getTradeType())
                .quantity(request.getQuantity())
                .priceAtTradeTime(tradePrice)
                .build();
        tradeRepository.save(trade);

        // Update user holdings
        UserHolding holding = userHoldingRepository
                .findByUserNameAndStockId(request.getUserName(), stock.getId());

        if (request.getTradeType() == TradeType.BUY) {
            if (holding == null) {
                holding = UserHolding.builder()
                        .userName(request.getUserName())
                        .stockId(stock.getId())
                        .quantity(request.getQuantity())
                        .buyPrice(tradePrice)
                        .build();
            } else {
                // Update existing holding
                Integer totalQuantity = holding.getQuantity() + request.getQuantity();
                // Weighted average
                double totalCost = holding.getBuyPrice() * holding.getQuantity()
                        + tradePrice * request.getQuantity();
                double newAvgPrice = totalCost / totalQuantity;

                holding.setQuantity(totalQuantity);
                holding.setBuyPrice(newAvgPrice);
            }
            userHoldingRepository.save(holding);

        } else if (request.getTradeType() == TradeType.SELL) {
            if (holding == null || holding.getQuantity() < request.getQuantity()) {
                throw new Exception("Insufficient quantity to sell");
            }
            // Decrease quantity
            Integer updatedQuantity = holding.getQuantity() - request.getQuantity();
            holding.setQuantity(updatedQuantity);

            // If quantity becomes 0, you can choose to remove the record or keep with zero quantity
            if (updatedQuantity == 0) {
                userHoldingRepository.delete(holding);
            }
            userHoldingRepository.save(holding);
        } else {
            throw new Exception("Invalid trade type. Allowed values: BUY or SELL");
        }

    }

    @Override
    public void closeMarket() {
        // Square off all open positions

        for(UserHolding holding : userHoldingRepository.findAll()){
            Stock stock = stockRepository.findById(holding.getStockId()).get();
            User user = userRepository.findByUserName(holding.getUserName());
            if(holding.getQuantity() > 0) {
                user.setLeveragedBalance(0.0);
                double pnl = (stock.getCurrentPrice() - holding.getBuyPrice()) * holding.getQuantity();
                user.setTotalBalance(user.getTotalBalance() + 1.1 * pnl);
                userHoldingRepository.delete(holding);
                userRepository.save(user);
            }
        }

    }
}
