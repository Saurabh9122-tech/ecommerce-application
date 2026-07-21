package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.entity.Order;
import com.saurabh.ecommerce.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String allOrders(Model model) {

        model.addAttribute("orders", orderService.getAllOrders());

        return "admin/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrder(@PathVariable Long id, Model model) {

        model.addAttribute("order",
                orderService.getOrderById(id));

        return "admin/edit-order";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute Order order) {

        Order existing = orderService.getOrderById(order.getId());

        existing.setStatus(order.getStatus());

        orderService.saveOrder(existing);

        return "redirect:/admin/orders";
    }
}