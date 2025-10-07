package com.api.ecommerce.infrastructure.mapper;

import com.api.ecommerce.domain.models.Order;
import com.api.ecommerce.domain.models.OrderItem;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.OrderResponse;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.OrderItemRequest;
import com.api.ecommerce.infrastructure.dto.OrderDTOs.OrderItemResponse;
import com.api.ecommerce.infrastructure.entity.OrderEntity;
import com.api.ecommerce.infrastructure.entity.OrderItemEntity;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductMapper.class})
public interface OrderMapper {


    @Mapping(target = "price", ignore = true)
    OrderItem toDomain(OrderItemRequest dto);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    OrderEntity toEntity(Order domain);

    @AfterMapping
    default void linkOrderItems(@MappingTarget OrderEntity orderEntity, Order order) {
        if (order.items() != null) {
            orderEntity.getItems().forEach(itemEntity -> itemEntity.setOrder(orderEntity));
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    OrderItemEntity toEntity(OrderItem itemDomain);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "items", target = "items")
    Order toDomain(OrderEntity entity);

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    OrderItem toDomain(OrderItemEntity itemEntity);

    List<Order> toDomainList(List<OrderEntity> entities);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "items", target = "items")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "status", target = "status")
    OrderResponse toResponseDTO(Order domain);


    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    OrderItemResponse toResponseDTO(OrderItem itemDomain);

    List<OrderResponse> toResponseDTOList(List<Order> domains);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDomain(@MappingTarget OrderEntity entity, Order domain);
}