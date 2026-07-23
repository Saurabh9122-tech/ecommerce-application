package com.saurabh.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="payments")
@Data
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // Payment method like UPI, CARD, CASH ON DELIVERY

    @Column(nullable = false)
    private String paymentMethod;




    // SUCCESS, PENDING, FAILED

    @Column(nullable = false)
    private String paymentStatus;




    // Payment amount

    private double amount;




    // Optional reverse mapping to Order

    @OneToOne(mappedBy = "payment")
    private Order order;


}