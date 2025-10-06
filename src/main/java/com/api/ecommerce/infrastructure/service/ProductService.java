package com.api.ecommerce.infrastructure.service;

import com.api.ecommerce.application.ports.in.service.ProductUC;
import com.api.ecommerce.application.ports.out.repository.ProductRepository;
import com.api.ecommerce.domain.models.Product;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductResponse;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductUpdateRequest;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductRequest;
import com.api.ecommerce.infrastructure.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements ProductUC {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest product) {
        final Product newProduct = productMapper.toDomain(product);

        final Product productSave = productRepository.save(newProduct);

        return productMapper.toResponseDTO(productSave);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductResponse> findProductById(UUID id) {
        return productRepository.findById(id).map(productMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductUpdateRequest productToUpdate) {
        if (productToUpdate.hasAtLeastOneUpdateField())
            throw new RuntimeException("Não existe ao menos um campo para atualizar");
        if (!productRepository.existsById(id))
            throw new RuntimeException("Produto não encontrado");

        final Product product = productMapper.toDomain(productToUpdate);

        final Product updatedProduct = productDataUpdate(id, product);

        productRepository.save(updatedProduct);

        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProductById(UUID id) {
        if (!productRepository.existsById(id))
            throw new RuntimeException("Produto não encontrado");

        productRepository.deleteById(id);

    }

    private Product productDataUpdate(UUID id, Product product) {
        product = product.copy()
                .id(id)
                .updatedAt(LocalDateTime.now())
                .build();

        Product recoveredProduct = productRepository.findById(product.id()).get();

        return product.copy()
                .createdAt(recoveredProduct.createdAt())
                .build();
    }
}
