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

    private Product productFirst;
    private Product productSecond;

    @BeforeEach
    void setUp() {
        productFirst = Product.builder()
                .name("Banana")
                .price(100.0)
                .creationDate(LocalDateTime.MIN)
                .id(1)
                .build();

        productSecond = Product.builder()
                .name("Apple")
                .price(100.0)
                .creationDate(LocalDateTime.MIN)
                .id(2)
                .build();

        entityManager.persist(productFirst);
        entityManager.persist(productSecond);
    }

    @Test
    @DisplayName("when Find By Id then Return Product")
    public void whenFindByIdThenReturnProduct() {
        Product expectedProduct = productRepository.findById(1).get();

        assertEquals(expectedProduct.getPrice(), productFirst.getPrice());
        assertEquals(expectedProduct.getName(), productFirst.getName());
        assertEquals(expectedProduct.getCreationDate(), productFirst.getCreationDate());
    }

    @Test
    @DisplayName("when FindBAll then Return Correct List Of Product")
    public void whenFindAllThenReturnCorrectListOfProduct() {
        List<Product> products = List.of(productFirst, productSecond);

        assertEquals(productRepository.findAll(), products);
    }

    @Test
    @DisplayName("when Delete By Id then Product Is Absent")
    public void whenDeleteByIdThenProductIsAbsent() {
        Product expectedProduct = productRepository.findById(2).get();
        assertEquals(expectedProduct.getName(), "Apple");

        productRepository.deleteById(2);

        assertFalse(productRepository.existsById(2));
    }

    @Test
    @DisplayName("when Find By Name then Return Product")
    public void whenFindByNameThenReturnProduct() {
        Product expectedProduct = productRepository.findByName("Banana").get(0);
        assertEquals(expectedProduct.getId(), productFirst.getId());
        assertEquals(expectedProduct.getName(), productFirst.getName());
        assertEquals(expectedProduct.getCreationDate(), productFirst.getCreationDate());
    }

    @Test
    @DisplayName("when Find All Then Size Of Product List Is Correct")
    public void whenFindAllThenSizeOfProductListIsCorrect() {
        assertEquals(2, productRepository.findAll().size());

        assertNotNull(entityManager);
    }

    @Test
    @DisplayName("when Delete Product Then Size Of Product List Changes Correct")
    public void whenDeleteProductThenSizeOfProductListChangesCorrect() {
        assertEquals(2, productRepository.findAll().size());

        productRepository.deleteById(1);
        assertEquals(1, productRepository.findAll().size());

        productRepository.deleteById(2);
        assertEquals(0, productRepository.findAll().size());
    }

    @Test
    @DisplayName("when Save Product Then Product Saves In Database Correct")
    public void whenSaveProductThenProductSavesInDatabaseCorrect() {
        productRepository.save(new Product(3, "Cheese", 200.0, LocalDateTime.now()));

        assertEquals(productRepository.findById(3).get().getName(), "Cheese");
        assertEquals(productRepository.findById(3).get().getPrice(), 200.0);
        assertFalse(productRepository.findByName("Cheese").isEmpty());
    }

    @Test
    @DisplayName("when Update Product Then Product Data Changes In Database Correct")
    public void whenUpdateProductThenProductDataChangesInDatabaseCorrect() {
        productRepository.save(new Product(3, "Butter", 80.0, LocalDateTime.now()));

        assertEquals(productRepository.findById(3).get().getName(), "Butter");
        assertEquals(productRepository.findById(3).get().getPrice(), 80.0);
        assertFalse(productRepository.findByName("Butter").isEmpty());
    }
}