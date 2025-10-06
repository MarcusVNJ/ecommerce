package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.ProductUC;
import com.api.ecommerce.infrastructure.controller.resource.ProductResource;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductResponse;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductRequest;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
public class CreateProductController implements ProductResource {

    private final ProductUC productUseCase;

    public CreateProductController(ProductUC productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        final ProductResponse product = productUseCase.createProduct(request);

        final URI location = productLocationGeneration(product.id());

        return ResponseEntity.created(location).body(product);
    }

    private URI productLocationGeneration(UUID id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/products/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
