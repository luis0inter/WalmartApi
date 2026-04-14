package com.walmartapi.service;

import com.walmartapi.entity.ProductEntity;
import com.walmartapi.exception.NotFound;
import com.walmartapi.mapper.CustomObjectMapper;
import com.walmartapi.model.Product;
import com.walmartapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomObjectMapper<ProductEntity, Product> productMapper;

    public ProductService(ProductRepository productRepository, CustomObjectMapper<ProductEntity, Product> productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Product saveProduct(Product product){
        ProductEntity newProduct = productMapper.mapToEntity(product);
        ProductEntity savedEntity = productRepository.save(newProduct);

        return productMapper.mapToDto(savedEntity);
    }

    public Product getProductById (Long id){
        Optional<ProductEntity> product = productRepository.findById(id);

        if(product.isEmpty()){
            throw new NotFound("Product not found");
        }

        return productMapper.mapToDto(product.get());
    }

    public Product updateProduct(Long id, Product product){
        Optional<ProductEntity> productOld = productRepository.findById(id);
        if(productOld.isPresent()){
            ProductEntity updatedProduct = productOld.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());
            productRepository.save(updatedProduct);
            return productMapper.mapToDto(updatedProduct);
        }
        throw new NotFound("Product not found");
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
