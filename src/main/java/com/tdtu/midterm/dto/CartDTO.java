package com.tdtu.midterm.dto;

import com.tdtu.midterm.model.Cart;
import com.tdtu.midterm.model.CartItem;
import lombok.Data;
import java.util.List;

@Data
public class CartDTO {
    private List<CartItem> cartItems;
    private double totalPrice;

    public static CartDTO fromCart(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setCartItems(cart.getCartItems());
        dto.setTotalPrice(cart.getTotalPrice());
        return dto;
    }
} 