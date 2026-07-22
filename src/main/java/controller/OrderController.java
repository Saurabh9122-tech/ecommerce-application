package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.service.InvoicePdfService;
import com.saurabh.ecommerce.entity.Order;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.CartService;
import com.saurabh.ecommerce.service.OrderService;
import com.saurabh.ecommerce.service.ProductService;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {


    private final OrderService orderService;
    private final CartService cartService;
    private final ProductService productService;
    private final InvoicePdfService invoicePdfService;


    public OrderController(OrderService orderService,
                           CartService cartService,
                           ProductService productService,
                           InvoicePdfService invoicePdfService) {

        this.orderService = orderService;
        this.cartService = cartService;
        this.productService = productService;
        this.invoicePdfService = invoicePdfService;

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



    // Cart checkout -> Payment
    @PostMapping("/place")
    public String placeOrder(
            @ModelAttribute Order order,
            HttpSession session) {


        order.setTotalAmount(
                cartService.getGrandTotal()
        );

        order.setStatus("PENDING");


        if(!cartService.getAllItems().isEmpty()) {

            order.setProduct(
                    cartService.getAllItems()
                            .get(0)
                            .getProduct()
            );
        }


        session.setAttribute(
                "pendingOrder",
                order
        );


        return "redirect:/payment";
    }




    // Buy Now -> Payment
    @PostMapping("/buy-place")
    public String buyPlaceOrder(
            @ModelAttribute Order order,
            @RequestParam Long productId,
            HttpSession session) {


        Product product =
                productService.getProductById(productId);


        order.setProduct(product);

        order.setTotalAmount(
                product.getPrice()
        );

        order.setStatus("PENDING");


        session.setAttribute(
                "pendingOrder",
                order
        );


        return "redirect:/payment";
    }




    @PostMapping("/cancel/{id}")
    public String cancelOrder(
            @PathVariable Long id,
            Authentication authentication) {


        Order order =
                orderService.getOrderById(id);


        if(!order.getEmail()
                .equals(authentication.getName())) {

            throw new RuntimeException(
                    "Unauthorized"
            );
        }


        if(!order.getStatus()
                .equals("PENDING")) {


            throw new RuntimeException(
                    "Order cannot be cancelled now"
            );

        }


        orderService.updateStatus(
                id,
                "CANCELLED"
        );


        return "redirect:/orders";

    }




    @GetMapping("/buy")
    public String buyNowCheckout(
            @RequestParam Long productId,
            Model model) {


        Product product =
                productService.getProductById(productId);


        model.addAttribute(
                "product",
                product
        );


        model.addAttribute(
                "order",
                new Order()
        );


        return "buy-checkout";

    }





    @GetMapping("/success")
    public String success(){

        return "order-success";

    }





    @GetMapping
    public String orderHistory(
            Authentication authentication,
            Model model) {


        String email =
                authentication.getName();


        model.addAttribute(
                "orders",
                orderService.getOrdersByEmail(email)
        );


        return "orders";

    }




    @GetMapping("/invoice/{id}")
    public String invoice(
            @PathVariable Long id,
            Model model) {


        Order order =
                orderService.getOrderById(id);


        model.addAttribute(
                "order",
                order
        );


        return "invoice";

    }

}