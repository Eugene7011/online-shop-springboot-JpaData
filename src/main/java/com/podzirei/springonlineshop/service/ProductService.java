package com.podzirei.springonlineshop.service;

import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.error.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findAll();

    void add(Product product);

    Product findById(int id) throws ProductNotFoundException;

    List<Product> findByName(String name);

    void update(Product product) throws ProductNotFoundException;

    void delete(int id) throws ProductNotFoundException;
}
