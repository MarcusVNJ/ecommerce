package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.ProductUC;
import com.api.ecommerce.infrastructure.controller.resource.ProductResource;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductResponse;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductUpdateRequest;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;


@RestController
public class ProductUpdateController implements ProductResource {
     private final ProductUC productUseCase;

     public ProductUpdateController(ProductUC productUseCase) {
         this.productUseCase = productUseCase;
     }

     @PatchMapping("/{id}")
     @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<ProductResponse> execute(@Valid @RequestBody ProductUpdateRequest request, @PathVariable("id") UUID id) {
         final ProductResponse product = productUseCase.updateProduct(id, request);

         final URI location = productLocationGeneration(product.id());

         return ResponseEntity.created(location).body(product);
     }

     private URI productLocationGeneration(UUID id) {
         return ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/{id}")
                 .buildAndExpand(id)
                 .toUri();
     }

}
