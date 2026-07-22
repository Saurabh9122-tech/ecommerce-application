package com.saurabh.ecommerce.controller;


import com.saurabh.ecommerce.entity.Order;
import com.saurabh.ecommerce.entity.Payment;
import com.saurabh.ecommerce.service.OrderService;
import com.saurabh.ecommerce.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/payment")
public class PaymentController {


    private final PaymentService paymentService;
    private final OrderService orderService;


    public PaymentController(
            PaymentService paymentService,
            OrderService orderService){

        this.paymentService = paymentService;
        this.orderService = orderService;

    }


    @GetMapping
    public String paymentPage(
            HttpSession session,
            Model model){

        Order order =
                (Order) session.getAttribute("pendingOrder");


        model.addAttribute("order", order);
        model.addAttribute("payment", new Payment());


        return "payment";
    }



    @PostMapping("/process")
    public String processPayment(
            @ModelAttribute Payment payment,
            HttpSession session){


        payment.setPaymentStatus("PAID");


        // save payment first
        Payment savedPayment =
                paymentService.makePayment(payment);



        // get pending order
        Order order =
                (Order) session.getAttribute("pendingOrder");



        // connect payment with order
        order.setPayment(savedPayment);


        // also save direct fields
        order.setPaymentStatus(savedPayment.getPaymentStatus());

        order.setPaymentMethod(savedPayment.getPaymentMethod());


        // save order
        orderService.saveOrder(order);



        session.removeAttribute("pendingOrder");


        return "redirect:/orders";

    }

}