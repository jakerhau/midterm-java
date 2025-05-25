package com.tdtu.midterm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class CartItem {

    @JsonBackReference
    private Cart cart;
    private Product product;
    private int quantity;
    private double price;

    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    public double getTotalPrice() {
        return price * quantity;
    }
} 