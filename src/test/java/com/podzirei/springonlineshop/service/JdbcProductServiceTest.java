package com.podzirei.springonlineshop.service;

import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.error.ProductNotFoundException;
import com.podzirei.springonlineshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

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
    @DisplayName("test Find By Name")
    public void testFindByName() {
        List<Product> productList = Arrays.asList(product);
        Mockito.when(productRepository.findByName("Banana"))
                .thenReturn(productList);

        Product actualProduct = jdbcProductService.findByName("Banana").get(0);

        assertEquals(product, actualProduct);
    }

    @Test
    @DisplayName("test Find All")
    public void testFindAll() {
        List<Product> productList = Arrays.asList(product);
        given(this.productRepository.findAll())
                .willReturn(productList);

        Product actualProduct = jdbcProductService.findAll().get(0);

        assertEquals(product, actualProduct);
    }

    @Test
    @DisplayName("when Find By Id then Correct Name Of Product Return")
    public void whenFindByName_thenCorrectNameOfProductReturn() throws ProductNotFoundException {
        given(this.productRepository.findById(1))
                .willReturn(Optional.ofNullable(product));

        Product actualProduct = jdbcProductService.findById(1);

        assertEquals("Banana", actualProduct.getName());
        assertEquals(100.0, actualProduct.getPrice());
    }
}