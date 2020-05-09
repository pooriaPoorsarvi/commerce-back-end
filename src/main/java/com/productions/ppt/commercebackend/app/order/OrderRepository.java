package com.productions.ppt.commercebackend.app.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    @Override
    Optional<OrderEntity> findById(Integer id);
}
