package com.arrow.server.service.impl;

import com.arrow.server.dto.CreateProductRequest;
import com.arrow.server.dto.ProductResponse;
import com.arrow.server.exception.ResourceNotFoundException;
import com.arrow.server.model.Product;
import com.arrow.server.model.ProductStatus;
import com.arrow.server.model.ProductType;
import com.arrow.server.repository.ProductRepository;
import com.arrow.server.repository.ReviewNoteRepository;
import com.arrow.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setIssuer(product.getIssuer());
        response.setType(product.getType());
        response.setUnderlyingAsset(product.getUnderlyingAsset());
        response.setProtectionLevel(product.getProtectionLevel());
        response.setTermMonths(product.getTermMonths());
        response.setSubmittedBy(product.getSubmittedBy());
        response.setSubmittedAt(product.getSubmittedAt());
        response.setStatus(product.getStatus());
        return response;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request){
        if(request.getName()==null || request.getName().isBlank()){
            throw new IllegalArgumentException("Product name is required");
        }

        if(request.getIssuer()==null || request.getIssuer().isBlank()){
            throw new IllegalArgumentException("Issuer is required");
        }

        Product product = new Product(
                request.getName(),
                request.getIssuer(),
                request.getType(),
                request.getUnderlyingAsset(),
                request.getProtectionLevel(),
                request.getTermMonths(),
                request.getSubmittedBy()
        );

        return  toResponse(productRepository.save(product));
    }

    // ── GET ONE ───────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return toResponse(product);
    }

    // ── GET ALL ───────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── GET BY STATUS ─────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── GET BY TYPE ───────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByType(ProductType type) {
        return productRepository.findByType(type)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── GET BY ISSUER ─────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByIssuer(String issuer) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getIssuer().toLowerCase().contains(issuer.toLowerCase()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── UPDATE STATUS ─────────────────────────────
    @Override
    @Transactional
    public ProductResponse updateStatus(Long id, ProductStatus newStatus) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // workflow validation — can't skip steps
        ProductStatus current = product.getStatus();
        if (current == ProductStatus.PENDING && newStatus == ProductStatus.APPROVED) {
            throw new IllegalArgumentException("Product must be UNDER_REVIEW before APPROVED");
        }
        if (current == ProductStatus.REJECTED) {
            throw new IllegalArgumentException("Cannot change status of a REJECTED product");
        }
        if (current == ProductStatus.APPROVED) {
            throw new IllegalArgumentException("Cannot change status of an APPROVED product");
        }

        product.setStatus(newStatus);
        return toResponse(productRepository.save(product));
    }
}