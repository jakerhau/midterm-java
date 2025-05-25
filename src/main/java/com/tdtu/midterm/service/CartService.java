package com.tdtu.midterm.service;

import com.tdtu.midterm.model.Cart;
import com.tdtu.midterm.model.CartItem;
import com.tdtu.midterm.model.Product;

import java.util.List;

public interface CartService {
    Cart getCart();

    void addToCart(Product product, int quantity);

    void removeFromCart(Product product);

    void updateQuantity(Product product, int quantity);

    List<CartItem> getCartItems();

    double getTotalPrice();

    void clearCart();
}
