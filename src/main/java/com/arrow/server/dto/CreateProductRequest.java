package com.arrow.server.dto;

import com.arrow.server.model.ProductType;

public class CreateProductRequest {

    private String name;
    private String issuer;
    private ProductType type;
    private String underlyingAsset;
    private Double protectionLevel;
    private Integer termMonths;
    private String submittedBy;

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
}
