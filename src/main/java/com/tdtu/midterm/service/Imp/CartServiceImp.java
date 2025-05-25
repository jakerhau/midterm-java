package com.tdtu.midterm.service.Imp;

import com.tdtu.midterm.model.Cart;
import com.tdtu.midterm.model.CartItem;
import com.tdtu.midterm.model.Product;
import com.tdtu.midterm.service.CartService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class CartServiceImp implements CartService {
    private Cart currentCart;

    @PostConstruct
    public void init() {
        // Khởi tạo cart mới khi service được tạo
        currentCart = new Cart();
        currentCart.setCartItems(new ArrayList<>());
        currentCart.setTotalPrice(0.0);
    }

    @Override
    public void addToCart(Product product, int quantity) {
        ensureCartExists();
        
        Optional<CartItem> existingItem = findCartItemByProduct(product);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setPrice(product.getPrice()); // Cập nhật giá mới nhất của sản phẩm
        } else {
            CartItem newItem = new CartItem(product, quantity);
            currentCart.addCartItem(newItem);
        }
        currentCart.updateTotalPrice();
    }

    @Override
    public void removeFromCart(Product product) {
        if (currentCart == null) return;
        
        currentCart.getCartItems().removeIf(item -> 
            item.getProduct().getId().equals(product.getId())
        );
        currentCart.updateTotalPrice();
    }

    @Override
    public void updateQuantity(Product product, int quantity) {
        if (currentCart == null) return;
        
        findCartItemByProduct(product).ifPresent(item -> {
            item.setQuantity(quantity);
            item.setPrice(product.getPrice()); // Cập nhật giá mới nhất
            currentCart.updateTotalPrice();
        });
    }

    @Override
    public List<CartItem> getCartItems() {
        ensureCartExists();
        return currentCart.getCartItems();
    }

    @Override
    public double getTotalPrice() {
        if (currentCart == null) return 0.0;
        return currentCart.getTotalPrice();
    }

    @Override
    public void clearCart() {
        if (currentCart != null) {
            currentCart.getCartItems().clear();
            currentCart.setTotalPrice(0.0);
        }
    }

    private Optional<CartItem> findCartItemByProduct(Product product) {
        if (currentCart == null) return Optional.empty();
        
        return currentCart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();
    }

    private void ensureCartExists() {
        if (currentCart == null) {
            init();
        }
    }

    @Override
    public Cart getCart() {
        return currentCart;
    }
}
