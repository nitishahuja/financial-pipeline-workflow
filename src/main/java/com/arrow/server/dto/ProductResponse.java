package com.arrow.server.dto;

import com.arrow.server.model.ProductStatus;
import com.arrow.server.model.ProductType;

import java.time.LocalDateTime;

public class ProductResponse {
    private Long id;
    private String name;
    private String issuer;
    private ProductType type;
    private String underlyingAsset;
    private Double protectionLevel;
    private Integer termMonths;
    private String submittedBy;
    private LocalDateTime submittedAt;
    private ProductStatus status;

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
