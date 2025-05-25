package com.tdtu.midterm.controller;

import com.tdtu.midterm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private CartService cartService;

    @ModelAttribute("cartItemCount")
    public int cartItemCount() {
        return cartService.getCartItems().size();
    }
} 