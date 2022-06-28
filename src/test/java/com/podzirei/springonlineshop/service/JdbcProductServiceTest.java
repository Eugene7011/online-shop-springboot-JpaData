package com.podzirei.springonlineshop.service;

import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.error.ProductNotFoundException;
import com.podzirei.springonlineshop.repository.ProductRepository;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private JdbcProductService jdbcProductService;

    private Product product;

    @BeforeEach
    void setUp() {

        product = Product.builder()
                .name("Banana")
                .price(100.0)
                .creationDate(LocalDateTime.MIN)
                .id(1)
                .build();
    }

    @Test
    @DisplayName("test Find By Id")
    public void testFindById() {
        List<Product> productList = Arrays.asList(product);
        Mockito.when(productRepository.findByName("Banana"))
                .thenReturn(productList);

        Product expectedProduct = productRepository.findByName("Banana").get(0);

        assertEquals(product, expectedProduct);
    }

    @Test
    @DisplayName("test Find All")
    public void testFindAll() {
        List<Product> productList = Arrays.asList(product);
        given(this.productRepository.findAll())
                .willReturn(productList);

        Product expectedProduct = jdbcProductService.findAll().get(0);

        assertEquals(product, expectedProduct);
    }

}