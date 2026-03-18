package com.arrow.server.controller;

import com.arrow.server.dto.CreateProductRequest;
import com.arrow.server.dto.ProductResponse;
import com.arrow.server.model.ProductStatus;
import com.arrow.server.model.ProductType;
import com.arrow.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // POST /api/products
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    // GET /api/products
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // GET /api/products/status/PENDING
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductResponse>> getByStatus(@PathVariable ProductStatus status) {
        return ResponseEntity.ok(productService.getProductsByStatus(status));
    }

    // GET /api/products/type/BUFFERED
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductResponse>> getByType(@PathVariable ProductType type) {
        return ResponseEntity.ok(productService.getProductsByType(type));
    }

    // GET /api/products/issuer/JPMorgan
    @GetMapping("/issuer/{issuer}")
    public ResponseEntity<List<ProductResponse>> getByIssuer(@PathVariable String issuer) {
        return ResponseEntity.ok(productService.getProductsByIssuer(issuer));
    }

    // PATCH /api/products/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam ProductStatus newStatus) {
        return ResponseEntity.ok(productService.updateStatus(id, newStatus));
    }
}