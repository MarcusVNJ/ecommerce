package com.api.ecommerce.infrastructure.repository;

import com.api.ecommerce.infrastructure.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringOrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {

    boolean existsByProductId(UUID productId);
}