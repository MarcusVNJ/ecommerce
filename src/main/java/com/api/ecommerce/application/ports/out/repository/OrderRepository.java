package com.api.ecommerce.application.ports.out.repository;

import com.api.ecommerce.domain.models.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(UUID id);
    
    List<Order> findByUserId(UUID userId);
}
