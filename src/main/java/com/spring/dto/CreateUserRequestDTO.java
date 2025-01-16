package com.spring.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequestDTO {
    private String userName;
    private String email;
    private List<PortfolioResponseDTO> holdings;
    private Double totalBalance;
}
