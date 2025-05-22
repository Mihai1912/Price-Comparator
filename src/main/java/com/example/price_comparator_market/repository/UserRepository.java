package com.example.price_comparator_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.price_comparator_market.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
