package com.podzirei.springonlineshop.controller;

import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.error.ProductNotFoundException;
import com.podzirei.springonlineshop.repository.ProductRepository;
import com.podzirei.springonlineshop.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.nullable;
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
    @DisplayName("when Find Products Saved Product From DB Should Return")
    void when_Find_Products_Saved_Product_From_DB_Should_Return() throws Exception {
        List<Product> productList = Arrays.asList(product);

        Mockito.when(productService.findAll())
                .thenReturn(productList);

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Banana"))
                .andExpect(jsonPath("$[0].price").value("100.0"))
                .andExpect(jsonPath("$[0].creationDate").value("-999999999-01-01T00:00:00"))
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    @DisplayName("when Get Request With Incorrect Url Status Not Found Should Return")
    void when_Get_Request_With_Incorrect_Url_Status_Not_Found_Should_Return() throws Exception {
         mockMvc.perform(get("/mistake")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("when Add Product With Correct Data Status OK Should Return")
    void when_Add_Product_With_Correct_Data_Status_OK_Should_Return() throws Exception {
        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                        {
                                        "name":"Banana",
                                        "price":"100",
                                        "creationDate":"2022-06-16T00:14:44.794951"
                                        }"""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when Add Product With Empty Data Status Bad Request Should Return")
    void when_Add_Product_With_Incorrect_Data_Status_Bad_Request_Should_Return() throws Exception {
        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when Update Product With Empty Data Status Bad Request Should Return")
    void when_Update_Product_With_Incorrect_Data_Status_Bad_Request_Should_Return() throws Exception {
        mockMvc.perform(put("/products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("when Add Product With Empty Data Status NotAllowed Should Return")
    void when_Add_Product_By_Incorrect_Url_Status_NotAllowed_Should_Return() throws Exception {
        mockMvc.perform(post("/products/mistake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                        {
                                        "name":"Banana",
                                        "price":"100",
                                        "creationDate":"2022-06-16T00:14:44.794951"
                                        }"""))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("when Find Product By Valid Id Status OK And Correct Product Should Return")
    void when_Find_Product_By_Valid_Id_Status_OK_And_Correct_Product_Should_Return() throws Exception {
        Mockito.when(productService.findById(1))
                .thenReturn(product);

        mockMvc.perform(get("/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Banana"))
                .andExpect(jsonPath("$.price").value("100.0"))
                .andExpect(jsonPath("$.creationDate").value("-999999999-01-01T00:00:00"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("when Find Product By Valid Name Status OK And Correct Product Should Return")
    void when_Find_Product_By_Valid_Name_Status_OK_And_Correct_Product_Should_Return() throws Exception {
        List<Product> productList = Arrays.asList(product);

        Mockito.when(productService.findByName("Banana"))
                .thenReturn(productList);

        mockMvc.perform(get("/products/search/Banana")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Banana"))
                .andExpect(jsonPath("$[0].price").value("100.0"))
                .andExpect(jsonPath("$[0].creationDate").value("-999999999-01-01T00:00:00"))
                .andExpect(jsonPath("$[0].id").value("1"));

        verify(productService).findByName("Banana");
    }

    @Test
    @DisplayName("when Update Product Status OK Should Return")
    void when_Update_Product_Status_OK_Should_Return() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"Banana",
                                "price":"200",
                                "id":"1"
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Delete Product Status OK Should Return")
    void when_Delete_Product_Status_OK_Should_Return() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/delete/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}