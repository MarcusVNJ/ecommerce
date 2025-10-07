package com.api.ecommerce.domain.models;

import com.api.ecommerce.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Order(
        UUID id,
        UUID userId,
        List<OrderItem> items,
        BigDecimal totalAmount,
        OrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public BigDecimal calculateTotalAmount() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order withCalculatedTotal() {
        BigDecimal calculatedTotal = calculateTotalAmount();
        return new Order(id, userId, items, calculatedTotal, status, createdAt, updatedAt);
    }

    public OrderBuilder copy() {
        return new OrderBuilder(this);
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private UUID id;
        private UUID userId;
        private List<OrderItem> items;
        private BigDecimal totalAmount;
        private OrderStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public OrderBuilder() {
        }

        public OrderBuilder(Order order) {
            this.id = order.id;
            this.userId = order.userId;
            this.items = order.items;
            this.totalAmount = order.totalAmount;
            this.status = order.status;
            this.createdAt = order.createdAt;
            this.updatedAt = order.updatedAt;
        }

        public OrderBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public OrderBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public OrderBuilder items(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public OrderBuilder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public OrderBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Order build() {
            return new Order(id, userId, items, totalAmount, status, createdAt, updatedAt);
        }

        public Order buildWithCalculatedTotal() {
            if (items != null && !items.isEmpty()) {
                this.totalAmount = items.stream()
                        .map(OrderItem::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            return new Order(id, userId, items, totalAmount, status, createdAt, updatedAt);
        }
    }
}
