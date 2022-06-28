package com.podzirei.springonlineshop.repository;

import com.podzirei.springonlineshop.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

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

        entityManager.merge(product);
        entityManager.flush();
    }

    @Test
    @DisplayName("")
    public void whenFindById_thenReturnProduct(){
        Product expectedProduct = productRepository.findById(1).get();
        assertEquals(expectedProduct.getPrice(), product.getPrice());
        assertEquals(expectedProduct.getName(), product.getName());
        assertEquals(expectedProduct.getCreationDate(), product.getCreationDate());
    }

    @Test
    @DisplayName("")
    public void whenFindByName_thenReturnProduct(){
        Product expectedProduct = productRepository.findByName("Banana").get(0);
        assertEquals(expectedProduct.getId(), product.getId());
        assertEquals(expectedProduct.getName(), product.getName());
        assertEquals(expectedProduct.getCreationDate(), product.getCreationDate());
    }

    @Test
    public void testCustomNativeQuery() {
        assertEquals(1, productRepository.findAll().size());

        assertNotNull(entityManager);
    }

}