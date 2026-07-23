package com.saurabh.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
@Data
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // Customer Details

    private String customerName;

    private String email;

    private String address;




    // Old payment fields
    // keep them because your database already has them

    private String paymentStatus;

    private String paymentMethod;




    // Order Amount

    private double totalAmount;



    private LocalDateTime orderDate;



    // Order Status

    @Column(nullable = false)
    private String status = "PENDING";






    // Product Purchased

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "product_id"
    )
    private Product product;






    // Payment Information

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "payment_id"
    )
    private Payment payment;



}