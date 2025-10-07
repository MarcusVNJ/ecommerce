package com.api.ecommerce.infrastructure.repository;

import com.api.ecommerce.application.ports.out.repository.OrderRepository;
import com.api.ecommerce.domain.models.Order;
import com.api.ecommerce.infrastructure.entity.OrderEntity;
import com.api.ecommerce.infrastructure.mapper.OrderMapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final SpringOrderRepository repository;
    private final OrderMapper mapper;

    public OrderRepositoryImpl(SpringOrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Order save(Order order) {
        final OrderEntity entity = mapper.toEntity(order);
        final OrderEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Order> findByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toDomain).toList();
    }
}
