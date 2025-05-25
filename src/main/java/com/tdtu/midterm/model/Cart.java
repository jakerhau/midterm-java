package com.tdtu.midterm.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    @JsonBackReference
    private List<CartItem> cartItems = new ArrayList<>();
    private double totalPrice;

    public void addCartItem(CartItem cartItem) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(cartItem);
        cartItem.setCart(this);
        updateTotalPrice();
    }

    public void removeCartItem(CartItem cartItem) {
        if (cartItems != null) {
            cartItems.remove(cartItem);
            cartItem.setCart(null);
            updateTotalPrice();
        }
    }

    public void updateTotalPrice() {
        this.totalPrice = cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    //get total price
    public double getTotalPrice() {
        return cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
}
