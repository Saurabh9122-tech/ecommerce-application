package com.saurabh.ecommerce.service;


import com.saurabh.ecommerce.entity.Payment;
import com.saurabh.ecommerce.repository.PaymentRepository;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {


    private final PaymentRepository paymentRepository;


    public PaymentService(PaymentRepository paymentRepository){

        this.paymentRepository = paymentRepository;

    }


    public Payment makePayment(Payment payment){

        return paymentRepository.save(payment);

    }

}