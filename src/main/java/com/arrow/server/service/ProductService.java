package com.arrow.server.service;

import com.arrow.server.dto.CreateProductRequest;
import com.arrow.server.dto.ProductResponse;
import com.arrow.server.model.ProductStatus;
import com.arrow.server.model.ProductType;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getProductsByStatus(ProductStatus status);
    List<ProductResponse> getProductsByType(ProductType type);
    List<ProductResponse> getProductsByIssuer(String issuer);
    ProductResponse updateStatus(Long id, ProductStatus status);
}