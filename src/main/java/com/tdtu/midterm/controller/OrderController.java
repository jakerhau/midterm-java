package com.tdtu.midterm.controller;

import com.tdtu.midterm.model.CartItem;
import com.tdtu.midterm.model.Order;
import com.tdtu.midterm.service.CartService;
import com.tdtu.midterm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping ("/checkout")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String showCheckoutPage(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        if(cartItems.isEmpty()) {
            model.addAttribute("message", "Giỏ hàng của bạn đang trống");
            return "redirect:/products";
        }
        double totalPrice = cartService.getTotalPrice();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart/checkout";
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    public String submitCheckout(@RequestParam String customerName,
                                 @RequestParam String customerPhone,
                                 @RequestParam String customerAddress,
                                 Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        Order order = orderService.createOrder(cartItems, customerName, customerPhone, customerAddress);
        model.addAttribute("customerName", customerName);
        model.addAttribute("customerPhone", customerPhone);
        model.addAttribute("customerAddress", customerAddress);
        model.addAttribute("order", order);
        return "cart/confirm";
    }
}
