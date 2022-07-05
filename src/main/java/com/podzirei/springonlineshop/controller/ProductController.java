package com.podzirei.springonlineshop.controller;

import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.service.ProductService;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/products")
    public List<Product> findAll() {
        List<Product> products = productService.findAll();
        logger.info("products {}", products);

        return products;
    }

    @PostMapping(path = "/products/add")
    protected void addProduct(@NotNull @RequestBody Product product) {
        logger.info("add product {}", product);
        productService.add(product);
    }

    @GetMapping(path = "/products/{id}")
    public Optional<Product> findById(@PathVariable int id) {
        Optional<Product> product = productService.findById(id);
        logger.info("findById {}", product);

        return product;
    }

    @GetMapping(path = "/products/search/{name}")
    public List<Product> findByName(@PathVariable String name) {
        List<Product> products = productService.findByName(name);
        logger.info("findByName {}", products);

        return products;
    }

    @PutMapping(path = "/products/update")
    public void updateProduct(@NotNull @RequestBody Product product) {
        logger.info("update {}", product);
        productService.update(product);
    }

    @DeleteMapping(path = "/products/delete/{id}")
    public void deleteProduct(@PathVariable int id) {
        logger.info("delete {}", id);
        productService.delete(id);
    }
}
