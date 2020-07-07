package com.productions.ppt.commercebackend.app.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    @Override
    Optional<OrderEntity> findById(Integer id);

    @Query("select c from OrderEntity as c where  c.owner.email = :email")
    List<OrderEntity> findByEmail(String email);
}
