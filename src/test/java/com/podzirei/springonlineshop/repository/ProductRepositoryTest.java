package com.podzirei.springonlineshop.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.error.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

@DBRider
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private final Timestamp timestamp = Timestamp.valueOf("2111-01-01 00:00:00.000000");
    private final LocalDateTime localDateTime = timestamp.toLocalDateTime();

    @Test
    @DisplayName("when Find By Id then Return Product")
    @DataSet("stored_products.yml")
    public void whenFindByIdThenReturnProduct() {
        assertEquals(3, productRepository.findAll().size());

        Product expectedProduct = productRepository.findById(1).get();

        assertEquals(expectedProduct.getPrice(), 100.0);
        assertEquals(expectedProduct.getName(), "Banana");
    }

    @Test
    @DataSet("stored_products.yml")
    @ExpectedDataSet("stored_products.yml")
    @DisplayName("when FindBAll then Return Correct List Of Product")
    public void whenFindAllThenReturnCorrectListOfProduct() {
        productRepository.findAll();
    }

    @Test
    @DataSet("stored_products.yml")
    @DisplayName("when Delete By Id then Product Is Absent")
    public void whenDeleteByIdThenProductIsAbsent() {
        productRepository.deleteById(2);

        assertFalse(productRepository.existsById(2));
    }

    @Test
    @DataSet("stored_products.yml")
    @ExpectedDataSet("expected_products.yml")
    @DisplayName("when Delete By Id Two Times then Product Is Absent")
    public void whenDeleteByIdTwoTimesThenProductIsAbsent() {
        productRepository.deleteById(2);
        productRepository.deleteById(3);
    }

    @Test
    @DataSet("stored_products.yml")
    @ExpectedDataSet("stored_products.yml")
    @DisplayName("when Find By Name then Return Product")
    public void whenFindByNameThenReturnProduct() {
        productRepository.findByName("Banana").get(0);
    }

    @Test
    @DataSet("stored_products.yml")
    @DisplayName("when Find All Then Size Of Product List Is Correct")
    public void whenFindAllThenSizeOfProductListIsCorrect() {
        assertEquals(3, productRepository.findAll().size());
    }

    @Test
    @DataSet("stored_products.yml")
    @DisplayName("when Delete Product Then Size Of Product List Changes Correct")
    public void whenDeleteProductThenSizeOfProductListChangesCorrect() {
        assertEquals(3, productRepository.findAll().size());

        productRepository.deleteById(3);
        assertEquals(2, productRepository.findAll().size());

        productRepository.deleteById(1);
        assertEquals(1, productRepository.findAll().size());
    }

    @Test
    @DataSet("empty_products.yml")
    @ExpectedDataSet("stored_products.yml")
    @DisplayName("when Save Product Then Product Saves In Database Correct")
    public void whenSaveProductThenProductSavesInDatabaseCorrect() {
        productRepository.save(new Product(1, "Banana", 100.0, localDateTime));
        productRepository.save(new Product(2, "Apple", 200.0, localDateTime));
        productRepository.save(new Product(3, "Cheese", 120.0, localDateTime));
    }

    @Test
    @DataSet("stored_products.yml")
    @ExpectedDataSet("updated_products.yml")
    @DisplayName("when Update Product Then Product Data Changes In Database Correct")
    public void whenUpdateProductThenProductDataChangesInDatabaseCorrect() {
        productRepository.save(new Product(1, "Carrot", 500.0, localDateTime));
        productRepository.deleteById(2);
        productRepository.deleteById(3);
    }
}