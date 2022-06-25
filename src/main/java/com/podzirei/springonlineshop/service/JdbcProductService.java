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
public class JdbcProductService implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void add(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productRepository.save(product);
    }

    @Override
    public Product findById(int id) {
        Optional<Product> product =
                productRepository.findById(id);

        if (!product.isPresent()){
            return null;
        }

        return product.get();
    }

    @Override
    public void update(Integer id, Product product) {
        product.setCreationDate(LocalDateTime.now());
        Product productFromDB = productRepository.findById(id).get();

        if (Objects.nonNull((product.getName())) &&
                !"".equalsIgnoreCase(product.getName())){
            productFromDB.setName(product.getName());
        }

        if (Objects.nonNull((product.getPrice())) &&
                !"".equalsIgnoreCase(String.valueOf(product.getPrice()))){
            productFromDB.setPrice(product.getPrice());
        }

        productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}