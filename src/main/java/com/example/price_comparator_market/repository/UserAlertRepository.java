package com.example.price_comparator_market.repository;

import com.example.price_comparator_market.entity.UserAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAlertRepository extends JpaRepository<UserAlert, Integer> {
    List<UserAlert> findByUsername(String username);
}
