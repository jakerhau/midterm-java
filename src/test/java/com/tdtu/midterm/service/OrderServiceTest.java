package com.tdtu.midterm.service;

import com.tdtu.midterm.model.*;
import com.tdtu.midterm.repository.OrderRepository;
import com.tdtu.midterm.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;
    private Cart testCart;
    private Product testProduct;
    private OrderItem testOrderItem;

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
        CartItem testCartItem = new CartItem();
        testCartItem.setId(1L);
        testCartItem.setProduct(testProduct);
        testCartItem.setQuantity(2);

        // Setup test cart
        testCart = new Cart();
        testCart.setId(1L);
        testCart.addItem(testCartItem);

        // Setup test order item
        testOrderItem = new OrderItem();
        testOrderItem.setId(1L);
        testOrderItem.setProduct(testProduct);
        testOrderItem.setQuantity(2);
        testOrderItem.setPrice(100.0);

        // Setup test order
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.addItem(testOrderItem);
    }

    @Test
    void createOrder_ShouldCreateNewOrder() {
        // Arrange
        when(cartService.getCart(1L)).thenReturn(testCart);
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // Act
        Order result = orderService.createOrder(1L);

        // Assert
        assertNotNull(result);
        assertEquals(OrderStatus.PENDING, result.getStatus());
        assertEquals(1, result.getItems().size());
        verify(cartService, times(1)).getCart(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void findById_WhenOrderExists_ShouldReturnOrder() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // Act
        Order result = orderService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testOrder.getId(), result.getId());
        assertEquals(testOrder.getStatus(), result.getStatus());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenOrderDoesNotExist_ShouldThrowException() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.findById(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void findAll_ShouldReturnAllOrders() {
        // Arrange
        List<Order> orders = Arrays.asList(testOrder);
        Page<Order> orderPage = new PageImpl<>(orders);
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orderPage);

        // Act
        Page<Order> result = orderService.findAll(PageRequest.of(0, 10));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testOrder, result.getContent().get(0));
        verify(orderRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void updateStatus_WhenOrderExists_ShouldUpdateStatus() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // Act
        Order result = orderService.updateStatus(1L, OrderStatus.COMPLETED);

        // Assert
        assertNotNull(result);
        assertEquals(OrderStatus.COMPLETED, result.getStatus());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void findByStatus_ShouldReturnOrdersWithStatus() {
        // Arrange
        List<Order> orders = Arrays.asList(testOrder);
        Page<Order> orderPage = new PageImpl<>(orders);
        when(orderRepository.findByStatus(any(OrderStatus.class), any(Pageable.class)))
                .thenReturn(orderPage);

        // Act
        Page<Order> result = orderService.findByStatus(OrderStatus.PENDING, PageRequest.of(0, 10));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testOrder, result.getContent().get(0));
        verify(orderRepository, times(1)).findByStatus(any(OrderStatus.class), any(Pageable.class));
    }
} 