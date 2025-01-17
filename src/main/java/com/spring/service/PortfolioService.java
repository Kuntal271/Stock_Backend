package com.spring.service;

import com.spring.dto.PortfolioResponseDTO;

import java.util.List;

public interface PortfolioService {

    public List<PortfolioResponseDTO> getUserPortfolio(String userName);
}
