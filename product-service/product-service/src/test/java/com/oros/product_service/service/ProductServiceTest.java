package com.oros.product_service.service;

import com.oros.product_service.dto.ProductRequest;
import com.oros.product_service.dto.ProductResponse;
import com.oros.product_service.mapper.ProductMapper;
import com.oros.product_service.model.Product;
import com.oros.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct() {
        ProductRequest request = ProductRequest.builder()
                .name("Laptop")
                .description("Gaming laptop")
                .price(BigDecimal.valueOf(1500))
                .build();

        Product product = Product.builder()
                .name("Laptop")
                .description("Gaming laptop")
                .price(BigDecimal.valueOf(1500))
                .build();

        when(productMapper.toEntity(request)).thenReturn(product);

        productService.createProduct(request);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldReturnAllProducts() {
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(1500))
                .build();

        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(1500))
                .build();

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toResponse(product)).thenReturn(response);

        List<ProductResponse> result = productService.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Laptop");
    }
}