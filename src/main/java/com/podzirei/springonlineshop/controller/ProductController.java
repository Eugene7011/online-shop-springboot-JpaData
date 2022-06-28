package com.podzirei.springonlineshop.controller;

import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.error.ProductNotFoundException;
import com.podzirei.springonlineshop.service.ProductService;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Product findById(@PathVariable int id) throws ProductNotFoundException {
        Product product = productService.findById(id);
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
    public void updateProduct(@NotNull @RequestBody Product product) throws ProductNotFoundException {
        logger.info("update {}", product);
        productService.update(product);
    }

    @DeleteMapping(path = "/products/delete/{id}")
    public void deleteProduct(@PathVariable int id) throws ProductNotFoundException {
        logger.info("delete {}", id);
        productService.delete(id);
    }
}
