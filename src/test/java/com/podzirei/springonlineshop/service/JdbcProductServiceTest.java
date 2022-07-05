package com.podzirei.springonlineshop.service;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.podzirei.springonlineshop.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DBRider
@SpringBootTest
public class JdbcProductServiceTest {

    private final Timestamp timestamp = Timestamp.valueOf("2111-01-01 00:00:00.000000");
    private final LocalDateTime localDateTime = timestamp.toLocalDateTime();

    @Autowired
    private JdbcProductService jdbcProductService;

    @Test
    @DataSet("stored_products.yml")
    @ExpectedDataSet("stored_products.yml")
    @DisplayName("test Find All")
    public void testFindAll() {
        jdbcProductService.findAll();
    }


    @Test
    @DisplayName("when Find By Id then Return Coorect Product")
    @DataSet("stored_products.yml")
    public void whenFindByIdThenReturnCorrectProduct() {
        assertEquals(3, jdbcProductService.findAll().size());

        Product expectedProduct = jdbcProductService.findById(1).get();

        assertEquals(expectedProduct.getPrice(), 100.0);
        assertEquals(expectedProduct.getName(), "Banana");
    }

    @Test
    @DataSet("stored_products.yml")
    @DisplayName("when Find By Name then Return Correct Product")
    public void whenFindByNameThenReturnCorrectProduct() {
        Product expectedProduct = jdbcProductService.findByName("Banana").get(0);

        assertEquals(expectedProduct.getPrice(), 100.0);
        assertEquals(expectedProduct.getName(), "Banana");
    }

    @Test
    @DataSet("stored_products.yml")
    @DisplayName("when Find Product By Not Valid Name then Return Empty List")
    public void whenFindProductByInvalidNameThenReturnEmptyList() {
        assertEquals(0, jdbcProductService.findByName("Error").size());
    }

    @Test
    @DataSet("stored_products.yml")
    @DisplayName("when Find Product By Invalid Id then Return Empty Result")
    public void whenFindProductByInvalidIdthenReturnEmptyResult() {
        assertEquals(Optional.empty(), jdbcProductService.findById(4));
    }

    @Test
    @DataSet("stored_products.yml")
    @DisplayName("when Delete Product Then Size Of Product List Changes Correct")
    public void whenDeleteProductThenSizeOfProductListChangesCorrect() {
        assertEquals(3, jdbcProductService.findAll().size());

        jdbcProductService.delete(3);
        assertEquals(2, jdbcProductService.findAll().size());

        jdbcProductService.delete(1);
        assertEquals(1, jdbcProductService.findAll().size());
    }

    @Test
    @DataSet("empty_products.yml")
    @ExpectedDataSet("stored_products.yml")
    @DisplayName("when Save Product Then Product Saves In Database Correct")
    public void whenSaveProductThenProductSavesInDatabaseCorrect() {
        jdbcProductService.add(new Product(1, "Banana", 100.0, localDateTime));
        jdbcProductService.add(new Product(2, "Apple", 200.0, localDateTime));
        jdbcProductService.add(new Product(3, "Cheese", 120.0, localDateTime));
    }

    @Test
    @DataSet("stored_products.yml")
    @ExpectedDataSet("expected_products.yml")
    @DisplayName("when Delete By Id Two Times then Product Is Absent")
    public void whenDeleteByIdTwoTimesThenProductIsAbsent() {
        jdbcProductService.delete(2);
        jdbcProductService.delete(3);
    }

    @Test
    @DataSet("stored_products.yml")
    @ExpectedDataSet("updated_products.yml")
    @DisplayName("when Update Product Then Product Data Changes In Database Correct")
    public void whenUpdateProductThenProductDataChangesInDatabaseCorrect() {
        jdbcProductService.update(new Product(1, "Carrot", 500.0, localDateTime));
        jdbcProductService.delete(2);
        jdbcProductService.delete(3);
    }
}