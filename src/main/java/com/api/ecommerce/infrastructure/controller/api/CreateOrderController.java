package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.OrderUC;
import com.api.ecommerce.infrastructure.controller.resource.OrderResource;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.CreateOrderRequest;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.OrderResponse;
import com.api.ecommerce.infrastructure.security.UserDetailsImpl;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CreateOrderController implements OrderResource {

    private final OrderUC orderUseCase;

    public CreateOrderController(OrderUC orderUC) {
        this.orderUseCase = orderUC;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponse> execute(
            @Valid @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderUseCase.createOrder(userDetails.getId(), request));
    }
}
