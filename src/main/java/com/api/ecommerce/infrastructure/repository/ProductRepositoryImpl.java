package com.api.ecommerce.infrastructure.repository;

import com.api.ecommerce.application.ports.out.repository.ProductRepository;
import com.api.ecommerce.domain.models.Product;
import com.api.ecommerce.infrastructure.entity.ProductEntity;
import com.api.ecommerce.infrastructure.mapper.ProductMapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private final SpringProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductRepositoryImpl(SpringProductRepository repository, ProductMapper mapper) {
        this.productRepository = repository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        var productEntity = mapper.toEntity(product);

        final ProductEntity productSave = productRepository.save(productEntity);

        return mapper.toDomain(productSave);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return productRepository.existsById(id);
    }
}
