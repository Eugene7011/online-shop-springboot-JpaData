package com.podzirei.springonlineshop.controller;

import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

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
    void findAll() throws Exception {
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Mockito.when(productService.findAll())
                .thenReturn(productList);

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void addProduct() throws Exception {
        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"Banana\",\n" +
                                "    \"price\": 100.0,\n" +
                                "    \"creationDate\": \"2022-06-16T00:14:44.794951\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        Mockito.when(productService.findById(1))
                .thenReturn(product);

        mockMvc.perform(get("/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.price").value("100.0"))
                .andExpect(jsonPath("$.creationDate").value("-999999999-01-01T00:00:00"))
                .andExpect(jsonPath("$.id").value("1"));

        verify(productService).findById(1);
    }

    @Test
    void findByName() {

    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}