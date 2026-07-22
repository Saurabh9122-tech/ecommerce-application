package com.saurabh.ecommerce.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.saurabh.ecommerce.entity.Order;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class InvoicePdfService {


    public void generate(Order order,
                         HttpServletResponse response) throws IOException {


        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=invoice-" + order.getId() + ".pdf"
        );


        Document document = new Document();

        PdfWriter.getInstance(
                document,
                response.getOutputStream()
        );


        document.open();


        Font title =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        20
                );


        document.add(
                new Paragraph("ShopEase Invoice", title)
        );


        document.add(
                new Paragraph("Invoice No : "
                        + order.getId())
        );


        document.add(
                new Paragraph("Customer : "
                        + order.getCustomerName())
        );


        document.add(
                new Paragraph("Email : "
                        + order.getEmail())
        );


        document.add(
                new Paragraph("Address : "
                        + order.getAddress())
        );


        document.add(
                new Paragraph(
                        "Product : "
                                + order.getProduct().getName()
                )
        );


        document.add(
                new Paragraph(
                        "Price : ₹ "
                                + order.getProduct().getPrice()
                )
        );


        document.add(
                new Paragraph(
                        "Total Amount : ₹ "
                                + order.getTotalAmount()
                )
        );


        document.add(
                new Paragraph(
                        "Status : "
                                + order.getStatus()
                )
        );


        document.close();

    }
}