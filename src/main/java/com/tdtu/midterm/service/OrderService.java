package com.tdtu.midterm.service;

import com.tdtu.midterm.model.CartItem;
import com.tdtu.midterm.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(List<CartItem> cartItems, String customerName, String customerPhone, String customerAddress);
}
