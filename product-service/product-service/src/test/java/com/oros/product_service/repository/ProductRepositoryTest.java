package com.oros.product_service.repository;

import com.oros.product_service.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndRetrieveProduct() {
        Product product = Product.builder()
                .name("Phone")
                .description("Smartphone")
                .price(BigDecimal.valueOf(999.99))
                .build();

        Product saved = productRepository.save(product);

        Optional<Product> found = productRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Phone");
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        Optional<Product> found = productRepository.findById(999L);

        assertThat(found).isEmpty();
    }
}