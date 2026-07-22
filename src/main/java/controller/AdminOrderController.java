package com.saurabh.ecommerce.controller;


import com.saurabh.ecommerce.service.OrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {


    private final OrderService orderService;


    public AdminOrderController(OrderService orderService){

        this.orderService = orderService;

    }



    // Admin view all orders
    @GetMapping
    public String orders(Model model){


        model.addAttribute("orders",
                orderService.getAllOrders());


        return "admin/orders";

    }



    // Admin update order status
    @PostMapping("/status/{id}")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status){


        orderService.updateStatus(id, status);


        return "redirect:/admin/orders";

    }


}