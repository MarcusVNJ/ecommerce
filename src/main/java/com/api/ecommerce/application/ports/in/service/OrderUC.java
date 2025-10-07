package com.api.ecommerce.application.ports.in.service;

import com.api.ecommerce.infrastructure.dto.OrderDTOs.OrderResponse;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.CreateOrderRequest;
import com.api.ecommerce.infrastructure.security.UserDetailsImpl;

import java.util.List;
import java.util.UUID;

public interface OrderUC {

    OrderResponse createOrder(UUID userId, CreateOrderRequest request);

    OrderResponse processPayment(UUID orderId, UserDetailsImpl user);

    List<OrderResponse> findOrdersByUserId(UUID userId);

}
