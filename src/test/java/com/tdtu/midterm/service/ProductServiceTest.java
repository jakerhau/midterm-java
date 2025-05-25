package com.tdtu.midterm.service;

import com.tdtu.midterm.model.Product;
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

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0);
        testProduct.setDescription("Test Description");
        testProduct.setStock(10);
    }

    @Test
    void findAll_ShouldReturnAllProducts() {
        // Arrange
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> productPage = new PageImpl<>(products);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

        // Act
        Page<Product> result = productService.findAll(PageRequest.of(0, 10));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testProduct, result.getContent().get(0));
        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Act
        Product result = productService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
        assertEquals(testProduct.getName(), result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.findById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void save_ShouldSaveAndReturnProduct() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // Act
        Product result = productService.save(testProduct);

        // Assert
        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
        assertEquals(testProduct.getName(), result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void delete_ShouldDeleteProduct() {
        // Arrange
        doNothing().when(productRepository).deleteById(1L);

        // Act
        productService.delete(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByCategory_ShouldReturnProductsInCategory() {
        // Arrange
        List<Product> products = Arrays.asList(testProduct);
        when(productRepository.findByCategoryId(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(products));

        // Act
        Page<Product> result = productService.findByCategory(1L, PageRequest.of(0, 10));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testProduct, result.getContent().get(0));
        verify(productRepository, times(1)).findByCategoryId(anyLong(), any(Pageable.class));
    }
} 