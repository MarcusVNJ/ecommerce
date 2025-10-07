package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.ProductUC;
import com.api.ecommerce.infrastructure.controller.resource.ProductResource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DeleteProductController implements ProductResource {
     private final ProductUC productUseCase;

     public DeleteProductController(ProductUC productUseCase) {
         this.productUseCase = productUseCase;
     }

     @DeleteMapping("/{id}")
     @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<Void> execute(@PathVariable UUID id) {
         productUseCase.deleteProductById(id);
         return ResponseEntity.noContent().build();
     }

}
