package com.api.ecommerce.infrastructure.dto;

import com.api.ecommerce.domain.enums.OrderStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderDTOs {

    public record OrderItemRequest(
            @NotNull UUID productId,
            @NotNull @Positive Integer quantity
    ) {}

    public record CreateOrderRequest(
            @NotEmpty @Valid List<OrderItemRequest> items
    ) {}

    public record OrderItemResponse(
            UUID productId,
            Integer quantity,
            BigDecimal price
    ) {}

    public record OrderResponse(
            UUID id,
            List<OrderItemResponse> items,
            BigDecimal totalAmount,
            OrderStatus status
    ) {}
}

