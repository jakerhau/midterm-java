package com.tdtu.midterm.api;

import com.tdtu.midterm.dto.CartDTO;
import com.tdtu.midterm.dto.Response;
import com.tdtu.midterm.model.Cart;
import com.tdtu.midterm.model.CartItem;
import com.tdtu.midterm.model.Product;
import com.tdtu.midterm.service.CartService;
import com.tdtu.midterm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public Response viewCart() {
        try {
            Cart cart = cartService.getCart();
            CartDTO cartDTO = CartDTO.fromCart(cart);
            return Response.builder(cartDTO)
                    .message("Cart retrieved successfully")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to retrieve cart: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/add")
    public Response addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1", required = false) int quantity) {
        try {
            Product product = productService.getProductById(productId);
            if (product != null) {
                cartService.addToCart(product, quantity);
                Cart cart = cartService.getCart();
                CartDTO cartDTO = CartDTO.fromCart(cart);
                return Response.builder(cartDTO)
                        .message("Product added to cart successfully")
                        .build();
            }
            return Response.builder()
                    .code(404)
                    .status("Error")
                    .message("Product not found")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to add to cart: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/remove")
    public Response removeFromCart(@RequestParam Long productId) {
        try {
            Product product = productService.getProductById(productId);
            if (product != null) {
                cartService.removeFromCart(product);
                Cart cart = cartService.getCart();
                CartDTO cartDTO = CartDTO.fromCart(cart);
                return Response.builder(cartDTO)
                        .message("Product removed from cart successfully")
                        .build();
            }
            return Response.builder()
                    .code(404)
                    .status("Error")
                    .message("Product not found")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to remove from cart: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/update")
    public Response updateQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        try {
            Product product = productService.getProductById(productId);
            if (product != null) {
                cartService.updateQuantity(product, quantity);
                Cart cart = cartService.getCart();
                CartDTO cartDTO = CartDTO.fromCart(cart);
                return Response.builder(cartDTO)
                        .message("Cart updated successfully")
                        .build();
            }
            return Response.builder()
                    .code(404)
                    .status("Error")
                    .message("Product not found")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to update cart: " + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/clear")
    public Response clearCart() {
        try {
            cartService.clearCart();
            Cart cart = cartService.getCart();
            CartDTO cartDTO = CartDTO.fromCart(cart);
            return Response.builder(cartDTO)
                    .message("Cart cleared successfully")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to clear cart: " + e.getMessage())
                    .build();
        }
    }
} 