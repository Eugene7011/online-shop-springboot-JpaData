package com.podzirei.springonlineshop.repository;

import com.podzirei.springonlineshop.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .name("Banana")
                .price(100.0)
                .creationDate(LocalDateTime.MIN)
                .id(1)
                .build();

        entityManager.persist(product);
    }

    @Test
    @DisplayName("")
    public void whenFindById_thenReturnProduct(){
        product = productRepository.findById(1).get();
        assertEquals(product.getName(), "Banana");
    }

}