package com.api.ecommerce.infrastructure.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductDTOs {
    public record ProductRequest(
            @NotBlank(message = "O nome não pode ser vazio.")
            String name,
            String description,
            @NotNull(message = "O preço não pode ser nulo.")
            @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
            BigDecimal price,
            @NotBlank(message = "A categoria não pode ser vazia.")
            String category,
            @NotNull(message = "A quantidade em estoque não pode ser nula.")
            @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa.")
            Integer stockQuantity
    ) {}

    public record ProductUpdateRequest(
            String name,
            String description,
            @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
            BigDecimal price,
            String category,
            @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa.")
            Integer stockQuantity
    ) {
        public boolean hasAtLeastOneUpdateField() {
            return (name == null || name.isBlank()) &&
                    (description == null || description.isBlank()) &&
                    price == null &&
                    (category == null || category.isBlank()) &&
                    stockQuantity == null;
        }
    }

    public record ProductResponse(
            UUID id,
            String name,
            String description,
            BigDecimal price,
            String category,
            Integer stockQuantity
    ) {}
}
