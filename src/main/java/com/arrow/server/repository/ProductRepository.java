package com.arrow.server.repository;

import com.arrow.server.model.Product;
import com.arrow.server.model.ProductStatus;
import com.arrow.server.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByType(ProductType type);
}