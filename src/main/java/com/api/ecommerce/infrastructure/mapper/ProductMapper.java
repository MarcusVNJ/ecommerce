package com.api.ecommerce.infrastructure.mapper;

import com.api.ecommerce.domain.models.Product;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductUpdateRequest;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductResponse;
import com.api.ecommerce.infrastructure.dto.ProductDTOs.ProductRequest;
import com.api.ecommerce.infrastructure.entity.ProductEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "category", target = "category"),
            @Mapping(source = "stockQuantity", target = "stockQuantity"),
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(source = "updatedAt", target = "updatedAt")
    })
    ProductEntity toEntity(Product product);

    @InheritInverseConfiguration
    Product toDomain(ProductEntity entity);

    @Mappings({

            @Mapping(source = "name", target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "description", target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "price", target = "price", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "category", target = "category", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "stockQuantity", target = "stockQuantity", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "id", ignore = true),
    })
    Product toDomain(ProductUpdateRequest product);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
    })
    Product toDomain(ProductRequest dto);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "category", target = "category"),
            @Mapping(source = "stockQuantity", target = "stockQuantity")
    })
    ProductResponse toResponseDTO(Product product);


    List<ProductResponse> toResponseDTOList(List<Product> products);

    List<Product> toDomainList(List<ProductEntity> entities);
}