package com.api.ecommerce.application.ports.in.service;

import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductResponse;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductUpdateRequest;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductUC {

    ProductResponse createProduct(ProductRequest product);

    Optional<ProductResponse> findProductById(UUID id);

    List<ProductResponse> findAllProducts();

    ProductResponse updateProduct(UUID id, ProductUpdateRequest product);

    void deleteProductById(UUID id);

}
