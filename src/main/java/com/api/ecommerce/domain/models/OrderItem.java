package com.api.ecommerce.domain.models;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(
        UUID productId,
        Integer quantity,
        BigDecimal price
) {

    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public OrderItemBuilder copy() {
        return new OrderItemBuilder(this);
    }

    public static class OrderItemBuilder {
        private UUID productId;
        private Integer quantity;
        private BigDecimal price;

        public OrderItemBuilder(OrderItem orderItem) {
            this.productId = orderItem.productId;
            this.quantity = orderItem.quantity;
            this.price = orderItem.price;
        }

        public OrderItemBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }

        public OrderItemBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(productId, quantity, price);
        }
    }
}
