package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.OrderUC;
import com.api.ecommerce.infrastructure.controller.resource.OrderResource;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.OrderResponse;
import com.api.ecommerce.infrastructure.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetUserOrdersController implements OrderResource {
    final OrderUC orderUseCase;

    public GetUserOrdersController(OrderUC orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrderResponse>> execute(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(orderUseCase.findOrdersByUserId(userDetails.getId()));
    }

}
