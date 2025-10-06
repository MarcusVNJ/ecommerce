package com.api.ecommerce.infrastructure.repository;

import com.api.ecommerce.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringProductRepository extends JpaRepository<ProductEntity, UUID> {
}