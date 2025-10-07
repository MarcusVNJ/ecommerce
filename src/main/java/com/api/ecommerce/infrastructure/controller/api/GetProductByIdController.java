package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.ProductUC;
import com.api.ecommerce.infrastructure.controller.resource.ProductResource;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetProductByIdController implements ProductResource {
     private final ProductUC productUseCase;

     public GetProductByIdController(ProductUC productUseCase) {
         this.productUseCase = productUseCase;
     }

     @GetMapping("/{id}")
     @PreAuthorize("hasRole('USER')")
     public ResponseEntity<ProductResponse> execute(@PathVariable UUID id) {
         return productUseCase.findProductById(id)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.notFound().build());
     }

}
