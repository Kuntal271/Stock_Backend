package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.entity.UserHolding;
import java.util.List;

@Repository
public interface UserHoldingRepository extends JpaRepository<UserHolding, Long> {

    List<UserHolding> findByUserId(Long userId);

    UserHolding findByUserIdAndStockId(Long userId, Long stockId);
}
