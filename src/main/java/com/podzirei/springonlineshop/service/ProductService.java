package com.podzirei.springonlineshop.service;

import com.podzirei.springonlineshop.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    List<Product> findAll();

    void add(Product product);

    Optional<Product> findById(int id);

    List<Product> findByName(String name);

    void update(Product product);

    void delete(int id);
}
