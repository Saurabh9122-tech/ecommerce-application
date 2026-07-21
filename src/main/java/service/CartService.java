package com.saurabh.ecommerce.service;

import com.saurabh.ecommerce.entity.CartItem;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.entity.User;
import com.saurabh.ecommerce.repository.CartRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;

    public CartService(CartRepository cartRepository,
                       UserService userService) {

        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userService.getUserByEmail(email);
    }

    @Transactional
    public void addToCart(Product product) {

        User user = getCurrentUser();

        CartItem item = cartRepository
                .findByProductAndUser(product, user)
                .orElse(null);

        if (item != null) {

            item.setQuantity(item.getQuantity() + 1);

        } else {

            item = new CartItem();

            item.setProduct(product);
            item.setUser(user);
            item.setQuantity(1);
        }

        item.setTotalPrice(
                item.getQuantity() * product.getPrice());

        cartRepository.save(item);
    }

    public List<CartItem> getAllItems() {

        return cartRepository.findByUser(getCurrentUser());

    }

    @Transactional
    public void removeItem(Long id) {

        cartRepository.deleteById(id);

    }

    @Transactional
    public void increaseQuantity(Long id) {

        CartItem item = cartRepository.findById(id).orElse(null);

        if (item != null) {

            item.setQuantity(item.getQuantity() + 1);

            item.setTotalPrice(
                    item.getQuantity() * item.getProduct().getPrice());

            cartRepository.save(item);
        }
    }

    @Transactional
    public void decreaseQuantity(Long id) {

        CartItem item = cartRepository.findById(id).orElse(null);

        if (item != null) {

            if (item.getQuantity() > 1) {

                item.setQuantity(item.getQuantity() - 1);

                item.setTotalPrice(
                        item.getQuantity() * item.getProduct().getPrice());

                cartRepository.save(item);

            } else {

                cartRepository.delete(item);

            }
        }
    }

    public double getGrandTotal() {

        return cartRepository.findByUser(getCurrentUser())
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();

    }

    public int getCartCount() {

        return cartRepository.findByUser(getCurrentUser()).size();

    }

    @Transactional
    public void clearCart() {

        cartRepository.deleteByUser(getCurrentUser());

    }
}