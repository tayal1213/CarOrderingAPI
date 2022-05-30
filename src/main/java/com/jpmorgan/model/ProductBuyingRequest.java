package com.jpmorgan.model;

public class ProductBuyingRequest {


    private String productName;
    private Integer quantity;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductBuyingRequest(String productName, Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;

    }

}
