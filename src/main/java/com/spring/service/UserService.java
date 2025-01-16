package com.spring.service;

import com.spring.dto.CreateUserRequestDTO;
import com.spring.dto.PortfolioResponseDTO;
import com.spring.entity.User;
import com.spring.entity.UserHolding;
import com.spring.repository.UserHoldingRepository;
import com.spring.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserHoldingRepository userPortfolioRepository;

    public UserService(UserRepository userRepository, UserHoldingRepository userPortfolioRepository) {
        this.userRepository = userRepository;
        this.userPortfolioRepository = userPortfolioRepository;
    }

    public void createUserWithPortfolio(CreateUserRequestDTO createUserRequest) {
        User user = new User();
        user.setUserName(createUserRequest.getUserName());
        user.setEmail(createUserRequest.getEmail());
        user.setTotalBalance(createUserRequest.getTotalBalance());
        user.setLeveragedBalance(0.0);
        userRepository.save(user);
        if (createUserRequest.getHoldings() == null)
            return;
        List<PortfolioResponseDTO> holdings = createUserRequest.getHoldings();
        for (PortfolioResponseDTO holding : holdings) {
            UserHolding portfolio = new UserHolding();
            portfolio.setUserName(user.getUserName());
            portfolio.setStockId(holding.getStockId());
            portfolio.setQuantity(holding.getQuantity());
            portfolio.setBuyPrice(holding.getBuyPrice());
            userPortfolioRepository.save(portfolio);
        }
    }
}
