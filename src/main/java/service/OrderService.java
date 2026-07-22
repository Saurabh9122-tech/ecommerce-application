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

    // Save Order
    public Order saveOrder(Order order) {

        order.setOrderDate(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // Get ALL Orders (Admin)
    public List<Order> getAllOrders() {

        return orderRepository.findAll();

    }

    // Get Orders of Logged-in User
    public List<Order> getOrdersByEmail(String email) {

        return orderRepository.findByEmailOrderByOrderDateDesc(email);

    }

    // Get Order By Id
    public Order getOrderById(Long id) {

        return orderRepository.findById(id).orElse(null);

    }
    public void updateStatus(Long id,String status){


        Order order =
                orderRepository.findById(id)
                        .orElseThrow();


        order.setStatus(status);


        orderRepository.save(order);


    }
    // Dashboard Statistics
    public long getOrderCount() {

        return orderRepository.count();

    }
    public Double getRevenue() {

        Double revenue = orderRepository.getTotalRevenue();

        return revenue == null ? 0.0 : revenue;

    }
}