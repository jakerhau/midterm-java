package com.tdtu.midterm.service;

import com.tdtu.midterm.model.Cart;
import com.tdtu.midterm.model.CartItem;
import com.tdtu.midterm.model.Product;
import com.tdtu.midterm.repository.CartRepository;
import com.tdtu.midterm.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private Cart testCart;
    private Product testProduct;
    private CartItem testCartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup test product
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0);
        testProduct.setStock(10);

        // Setup test cart item
        testCartItem = new CartItem();
        testCartItem.setId(1L);
        testCartItem.setProduct(testProduct);
        testCartItem.setQuantity(2);

        // Setup test cart
        testCart = new Cart();
        testCart.setId(1L);
        testCart.addItem(testCartItem);
    }

    @Test
    void addToCart_WhenProductExists_ShouldAddItem() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(cartRepository.save(any(Cart.class))).thenReturn(testCart);

        // Act
        Cart result = cartService.addToCart(1L, 2);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals(2, result.getItems().get(0).getQuantity());
        verify(productRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void addToCart_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartService.addToCart(1L, 2));
        verify(productRepository, times(1)).findById(1L);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void updateCartItem_WhenItemExists_ShouldUpdateQuantity() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));
        when(cartRepository.save(any(Cart.class))).thenReturn(testCart);

        // Act
        Cart result = cartService.updateCartItem(1L, 1L, 3);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.getItems().get(0).getQuantity());
        verify(cartRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void removeFromCart_WhenItemExists_ShouldRemoveItem() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));
        when(cartRepository.save(any(Cart.class))).thenReturn(testCart);

        // Act
        Cart result = cartService.removeFromCart(1L, 1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        verify(cartRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void clearCart_ShouldRemoveAllItems() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));
        when(cartRepository.save(any(Cart.class))).thenReturn(testCart);

        // Act
        Cart result = cartService.clearCart(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        verify(cartRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void getCart_WhenCartExists_ShouldReturnCart() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));

        // Act
        Cart result = cartService.getCart(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testCart.getId(), result.getId());
        assertEquals(1, result.getItems().size());
        verify(cartRepository, times(1)).findById(1L);
    }

    @Test
    void getCart_WhenCartDoesNotExist_ShouldCreateNewCart() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(testCart);

        // Act
        Cart result = cartService.getCart(1L);

        // Assert
        assertNotNull(result);
        verify(cartRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }
} 