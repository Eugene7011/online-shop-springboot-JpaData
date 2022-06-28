package com.podzirei.springonlineshop.service;

import com.podzirei.springonlineshop.entity.Product;
import com.podzirei.springonlineshop.error.ProductNotFoundException;
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
    public Product findById(int id) throws ProductNotFoundException {
        Optional<Product> product =
                productRepository.findById(id);

        if (!product.isPresent()){
            throw new ProductNotFoundException("Product is not found");
        }

        return product.get();
    }

    @Override
    public void update(Product product) throws ProductNotFoundException {
        Product productFromDB = findById(product.getId());

        if (Objects.nonNull((product.getName())) &&
                !"".equalsIgnoreCase(product.getName())){
            productFromDB.setName(product.getName());
        }

        if (Objects.nonNull((product.getPrice())) &&
                !"".equals(String.valueOf(product.getPrice()))){
            productFromDB.setPrice(product.getPrice());
        }
        product.setCreationDate(productFromDB.getCreationDate());

        productRepository.save(product);
    }

    @Override
    public void delete(int id) throws ProductNotFoundException {
        Optional<Product> product =
                productRepository.findById(id);
        if (product.isEmpty()){
            throw new ProductNotFoundException("Product is not found");
        }
        productRepository.deleteById(id);
    }
}
