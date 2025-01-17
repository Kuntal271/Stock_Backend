package com.spring.service;

import com.spring.dto.TradeRequestDTO;

public interface TradeService {

    public void executeTrade(TradeRequestDTO request) throws Exception;

    public void closeMarket();

}
