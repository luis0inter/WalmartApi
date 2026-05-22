package com.walmartapi.service;

import com.walmartapi.entity.CategoryEntity;
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
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService,CustomObjectMapper<ProductEntity, Product> productMapper){
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    public Product saveProduct(Product product){
        CategoryEntity categoryEntity = categoryService.findEntityById(product.getCategoryId());
        ProductEntity newProduct = productMapper.mapToEntity(product);
        //AQUI
        newProduct.setCategory(categoryEntity);
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
