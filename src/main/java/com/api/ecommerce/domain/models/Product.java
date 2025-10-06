package com.api.ecommerce.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Product(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        String category,
        Integer stockQuantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public ProductBuilder copy() {
        return new ProductBuilder(this);
    }

    public static class ProductBuilder {
        private UUID id;
        private String name;
        private String description;
        private BigDecimal price;
        private String category;
        private Integer stockQuantity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ProductBuilder(Product product) {
            this.id = product.id;
            this.name = product.name;
            this.description = product.description;
            this.price = product.price;
            this.category = product.category;
            this.stockQuantity = product.stockQuantity;
            this.createdAt = product.createdAt;
            this.updatedAt = product.updatedAt;
        }

        public ProductBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder category(String category) {
            this.category = category;
            return this;
        }

        public ProductBuilder stockQuantity(Integer stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public ProductBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Product build() {
            return new Product(id, name, description, price, category, stockQuantity, createdAt, updatedAt);
        }
    }
}
