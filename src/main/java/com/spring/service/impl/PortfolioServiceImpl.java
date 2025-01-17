package com.spring.service.impl;

import com.spring.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dto.PortfolioResponseDTO;
import com.spring.entity.Stock;
import com.spring.entity.UserHolding;
import com.spring.repository.StockRepository;
import com.spring.repository.UserHoldingRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final UserHoldingRepository userHoldingRepository;
    private final StockRepository stockRepository;

    @Override
    public List<PortfolioResponseDTO> getUserPortfolio(String userName) {
        List<UserHolding> holdings = userHoldingRepository.findByUserName(userName);

        return holdings.stream()
                .map(holding -> {
                    Stock stock = stockRepository.findById(holding.getStockId()).orElse(null);
                    if (stock == null) return null;

                    double currentPrice = stock.getCurrentPrice();
                    double buyPrice = holding.getBuyPrice();
                    double gainLoss = currentPrice - buyPrice;
                    double gainLossPercent = (buyPrice == 0.0) ? 0.0 : (gainLoss / buyPrice * 100);

                    PortfolioResponseDTO dto = new PortfolioResponseDTO();
                    dto.setStockId(stock.getId());
                    dto.setStockName(stock.getStockName());
                    dto.setQuantity(holding.getQuantity());
                    dto.setBuyPrice(buyPrice);
                    dto.setCurrentPrice(currentPrice);
                    dto.setGainLoss(gainLoss);
                    dto.setGainLossPercent(gainLossPercent);
                    return dto;
                })
                .filter(dto -> dto != null) // Filter out invalid stocks if any
                .collect(Collectors.toList());
    }
}
