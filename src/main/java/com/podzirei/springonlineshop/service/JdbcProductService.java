package com.podzirei.springonlineshop.service;

import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JdbcProductService implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void add(Product product) {
        if (product.getCreationDate() == null) {
            product.setCreationDate(LocalDateTime.now());
        }
        productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public void update(Product product) {
        Product productFromDB = findById(product.getId()).get();

        if (Objects.nonNull((product.getName())) &&
                !"".equalsIgnoreCase(product.getName())) {
            productFromDB.setName(product.getName());
        }

        productFromDB.setPrice(product.getPrice());

        if (Objects.nonNull(product.getCreationDate())) {
            productFromDB.setCreationDate(product.getCreationDate());
        }

        productRepository.save(productFromDB);
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
