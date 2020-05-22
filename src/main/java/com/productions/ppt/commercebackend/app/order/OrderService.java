package com.productions.ppt.commercebackend.app.order;

import java.util.Optional;

public interface OrderService {
  Optional<OrderEntity> findById(Integer id);
  void save(OrderEntity order);
}
