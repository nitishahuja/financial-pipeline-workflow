package com.arrow.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String issuer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;

    @Column(nullable = false)
    private String underlyingAsset;

    @Column(nullable = false)
    private Double protectionLevel;

    @Column(nullable = false)
    private Integer termMonths;

    @Column(nullable = false)
    private String submittedBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    public Product() {}

    public Product(String name, String issuer, ProductType type,
                   String underlyingAsset, Double protectionLevel,
                   Integer termMonths, String submittedBy) {
        this.name            = name;
        this.issuer          = issuer;
        this.type            = type;
        this.underlyingAsset = underlyingAsset;
        this.protectionLevel = protectionLevel;
        this.termMonths      = termMonths;
        this.submittedBy     = submittedBy;
        this.submittedAt     = LocalDateTime.now();
        this.status          = ProductStatus.PENDING;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getUnderlyingAsset() {
        return underlyingAsset;
    }

    public void setUnderlyingAsset(String underlyingAsset) {
        this.underlyingAsset = underlyingAsset;
    }

    public Double getProtectionLevel() {
        return protectionLevel;
    }

    public void setProtectionLevel(Double protectionLevel) {
        this.protectionLevel = protectionLevel;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
