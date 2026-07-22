package com.saurabh.ecommerce.service;

import com.saurabh.ecommerce.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {


    // GST calculation
    public double calculateGST(double amount){

        return amount * 0.18; // 18% GST

    }



    // CGST
    public double calculateCGST(double amount){

        return amount * 0.09; // 9% CGST

    }



    // SGST
    public double calculateSGST(double amount){

        return amount * 0.09; // 9% SGST

    }



    // Final amount including GST
    public double calculateFinalAmount(double amount){

        return amount + calculateGST(amount);

    }



}