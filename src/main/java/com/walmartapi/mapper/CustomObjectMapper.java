package com.walmartapi.mapper;

import com.walmartapi.entity.ProductEntity;
import com.walmartapi.model.Product;

public interface CustomObjectMapper<E, D> {
    E mapToEntity(D dto);
    D mapToDto(E entity);
}
