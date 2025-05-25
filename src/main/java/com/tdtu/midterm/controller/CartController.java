package com.tdtu.midterm.controller;

import com.tdtu.midterm.model.CartItem;
import com.tdtu.midterm.model.Product;
import com.tdtu.midterm.service.CartService;
import com.tdtu.midterm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewCart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        double totalPrice = cartService.getTotalPrice();
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart/index";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1", required = false) int quantity) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            cartService.addToCart(product, quantity);
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            cartService.removeFromCart(product);
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            cartService.updateQuantity(product, quantity);
        }
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}
