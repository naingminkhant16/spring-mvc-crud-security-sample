package com.moe.demo.service;

import com.moe.demo.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    void save(Product product);

    Product findById(Long id);

    void delete(Product product);
}
