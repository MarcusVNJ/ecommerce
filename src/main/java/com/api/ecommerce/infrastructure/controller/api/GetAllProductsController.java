package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.ProductUC;
import com.api.ecommerce.infrastructure.controller.resource.ProductResource;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllProductsController implements ProductResource {
     private final ProductUC productUseCase;

     public GetAllProductsController(ProductUC productUseCase) {
         this.productUseCase = productUseCase;
     }

     @GetMapping
     @PreAuthorize("hasRole('USER')")
     public ResponseEntity<List<ProductResponse>> execute() {
         return ResponseEntity.ok(productUseCase.findAllProducts());
     }

}
