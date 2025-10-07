package com.api.ecommerce.infrastructure.service;

import com.api.ecommerce.application.ports.in.service.OrderUC;
import com.api.ecommerce.application.ports.out.repository.OrderRepository;
import com.api.ecommerce.application.ports.out.repository.ProductRepository;
import com.api.ecommerce.domain.exception.BusinessRuleException;
import com.api.ecommerce.domain.exception.MappingException;
import com.api.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.ecommerce.domain.enums.OrderStatus;
import com.api.ecommerce.domain.models.Order;
import com.api.ecommerce.domain.models.OrderItem;
import com.api.ecommerce.domain.models.Product;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.OrderResponse;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.CreateOrderRequest;
import com.api.ecommerce.infrastructure.mapper.OrderMapper;
import com.api.ecommerce.infrastructure.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements OrderUC {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(UUID userId, CreateOrderRequest request) {

        List<OrderItem> items = getOrderItems(request);

        List<OrderItem> validatedItems = itemValidation(items);

        Order newOrder = orderRepository.save(createNewOrder(userId, validatedItems));

        return orderMapper.toResponseDTO(newOrder);
    }

    @Override
    @Transactional
    public OrderResponse processPayment(UUID orderId, UserDetailsImpl user) {

        if (!isOrderOwnedByUser(orderId, user))
            throw new ResourceNotFoundException("Pedido não encontrado ou não pertence ao usuário.");

        Optional<Order> orderProcessed = orderRepository.findById(orderId)
                .map(order -> {
                    if (order.status() == OrderStatus.PENDING) {
                        Order updatedOrder = order.copy()
                                .status(OrderStatus.PAID)
                                .build();
                        return orderRepository.save(updatedOrder);
                    }
                    return order;
                });

        return orderProcessed.map(orderMapper::toResponseDTO).orElseThrow(() ->
                new MappingException("Pedido não encontrado após tentativa de processamento."));
    }

    @Override
    public List<OrderResponse> findOrdersByUserId(UUID userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(orderMapper::toResponseDTO).toList();
    }

    private List<OrderItem> itemValidation(List<OrderItem> items) {
        return items.stream().map(item -> {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + item.productId() + " não encontrado."));

            if (product.stockQuantity() < item.quantity())
                throw new BusinessRuleException("Estoque insuficiente para o produto: " + product.name());

            productRepository.save(product.copy().stockQuantity(product.stockQuantity() - item.quantity()).build());

            return item.copy().price(product.price()).build();
        }).toList();
    }

    private boolean isOrderOwnedByUser(UUID orderId, UserDetailsImpl user) {
        Order order = findOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido com ID " + orderId + " não encontrado."));
        return order.userId().equals(user.getId());
    }

    private Optional<Order> findOrderById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    private Order createNewOrder(UUID userId, List<OrderItem> validatedItems) {
        return Order.builder()
                .userId(userId)
                .items(validatedItems)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .buildWithCalculatedTotal();
    }

    private List<OrderItem> getOrderItems(CreateOrderRequest request) {
        return request.items().stream().map(orderMapper::toDomain).toList();
    }

}
