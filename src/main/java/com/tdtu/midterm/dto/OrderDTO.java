package com.tdtu.midterm.dto;

import com.tdtu.midterm.model.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private List<CartItem> cartItemList;
    private double totalPrice;

    public static OrderDTO fromCart(List<CartItem> cartItems, double totalPrice) {
        OrderDTO dto = new OrderDTO();
        dto.setCartItemList(cartItems);
        dto.setTotalPrice(totalPrice);
        return dto;
    }
}
