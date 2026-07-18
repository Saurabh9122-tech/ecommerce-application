package com.saurabh.ecommerce.service;

import com.saurabh.ecommerce.entity.CartItem;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(Product product) {

        CartItem item = cartRepository.findByProduct(product).orElse(null);

        if(item != null){

            item.setQuantity(item.getQuantity() + 1);

            item.setTotalPrice(item.getQuantity() * product.getPrice());

        }else{

            item = new CartItem();

            item.setProduct(product);

            item.setQuantity(1);

            item.setTotalPrice(product.getPrice());

        }

        cartRepository.save(item);

    }

    public List<CartItem> getAllItems() {
        return cartRepository.findAll();
    }

    public void removeItem(Long id) {
        cartRepository.deleteById(id);
    }

    public double getGrandTotal() {

        return cartRepository.findAll()
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
}