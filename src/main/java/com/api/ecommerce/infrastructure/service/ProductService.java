package com.api.ecommerce.infrastructure.service;

import com.api.ecommerce.application.ports.in.service.ProductUC;
import com.api.ecommerce.application.ports.out.repository.ProductRepository;
import com.api.ecommerce.domain.models.Product;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductResponse;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductUpdateRequest;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductRequest;
import com.api.ecommerce.infrastructure.repository.SpringOrderItemRepository;
import com.api.ecommerce.infrastructure.mapper.ProductMapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements ProductUC {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final SpringOrderItemRepository orderItemRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, SpringOrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.orderItemRepository = orderItemRepository;
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
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não existe ao menos um campo para atualizar");
        if (!productRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");

        final Product product = productMapper.toDomain(productToUpdate)
                .copy().id(id).build();

        productRepository.save(product);

        return productMapper.toResponseDTO(product);
    }

    @Override
    @Transactional
    public void deleteProductById(UUID id) {
        if (!productRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");

        if (orderItemRepository.existsByProductId(id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível deletar o produto, pois ele está associado a um ou mais pedidos.");

        productRepository.deleteById(id);
    }

}
