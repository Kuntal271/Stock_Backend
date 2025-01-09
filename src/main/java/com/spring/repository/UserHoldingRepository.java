package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.entity.UserHolding;
import java.util.List;

@Repository
public interface UserHoldingRepository extends JpaRepository<UserHolding, String> {

    List<UserHolding> findByUserName(String userName);

    UserHolding findByUserIdAndStockId(Long userId, Long stockId);
}
