package com.api.ecommerce.application.ports.out.repository;

import com.api.ecommerce.domain.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    void deleteById(UUID id);

    boolean existsById(UUID id);
}
