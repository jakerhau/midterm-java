package com.tdtu.midterm.api;

import com.tdtu.midterm.dto.OrderDTO;
import com.tdtu.midterm.dto.Response;
import com.tdtu.midterm.model.CartItem;
import com.tdtu.midterm.model.Order;
import com.tdtu.midterm.service.CartService;
import com.tdtu.midterm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderApiController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping
    public Response getCheckoutInfo() {
        try {
            List<CartItem> cartItems = cartService.getCartItems();
            if(cartItems.isEmpty()) {
                return Response.builder()
                        .code(400)
                        .status("Error")
                        .message("Cart is empty")
                        .build();
            }
            double totalPrice = cartService.getTotalPrice();
            OrderDTO orderDTO = OrderDTO.fromCart(cartItems, totalPrice);
            return Response.builder(orderDTO)
                    .message("Checkout information retrieved successfully")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to get checkout information: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public Response createOrder(@RequestParam String customerName,
                              @RequestParam String customerPhone,
                              @RequestParam String customerAddress) {
        try {
            List<CartItem> cartItems = cartService.getCartItems();
            if(cartItems.isEmpty()) {
                return Response.builder()
                        .code(400)
                        .status("Error")
                        .message("Cart is empty")
                        .build();
            }
            Order order = orderService.createOrder(cartItems, customerName, customerPhone, customerAddress);
            return Response.builder(order)
                    .message("Order created successfully")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to create order: " + e.getMessage())
                    .build();
        }
    }
} 