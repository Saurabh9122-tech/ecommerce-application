package com.saurabh.ecommerce.service;

import com.saurabh.ecommerce.entity.Order;
import com.saurabh.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order) {

        order.setOrderDate(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {

        return orderRepository.findAllByOrderByOrderDateDesc();

    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

}