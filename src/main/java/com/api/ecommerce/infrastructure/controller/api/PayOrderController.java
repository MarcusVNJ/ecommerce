package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.OrderUC;
import com.api.ecommerce.infrastructure.controller.resource.OrderResource;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.OrderResponse;

import com.api.ecommerce.infrastructure.security.UserDetailsImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PayOrderController implements OrderResource {
    private final OrderUC orderUseCase;

    public PayOrderController(OrderUC orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping("/{orderId}/pay")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponse> execute(
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(orderUseCase.processPayment(orderId, userDetails));
    }
}
