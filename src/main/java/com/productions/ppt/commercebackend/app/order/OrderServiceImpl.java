package com.productions.ppt.commercebackend.app.order;

import org.springframework.stereotype.Component;

import java.util.Optional;

//TODO update the Impl names to something more suiting
@Component
class OrderServiceImpl implements OrderService  {
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderEntity> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public void save(OrderEntity order) {
        orderRepository.save(order);
    }
}
