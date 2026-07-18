package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.entity.Order;
import com.saurabh.ecommerce.service.CartService;
import com.saurabh.ecommerce.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService,
                           CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {

        model.addAttribute("order", new Order());

        model.addAttribute("cartItems",
                cartService.getAllItems());

        model.addAttribute("grandTotal",
                cartService.getGrandTotal());

        return "checkout";
    }

    @PostMapping("/place")
    public String placeOrder(@ModelAttribute Order order) {

        order.setTotalAmount(cartService.getGrandTotal());

        orderService.saveOrder(order);

        cartService.clearCart();

        return "redirect:/orders/success";
    }

    @GetMapping("/success")
    public String success() {
        return "order-success";
    }
    @GetMapping
    public String orderHistory(Model model) {

        model.addAttribute("orders",
                orderService.getAllOrders());

        return "orders";

    }
}