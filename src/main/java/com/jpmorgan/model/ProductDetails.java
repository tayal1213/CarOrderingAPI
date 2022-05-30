package com.jpmorgan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "PRODUCT_DETAILS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "PRODUCT_ID"),
        @UniqueConstraint(columnNames = "PRODUCT_NAME")})
public class ProductDetails {
    

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PRODUCT_ID", unique = true, nullable = false)
    private Long productId;

    @Column(name = "PRODUCT_NAME" ,nullable = false)
    private String productName;

    @Column(name = "STOCK_AVAILABLE" ,nullable = false)
    private Integer stockAvailble;

    @Column(name = "PRODUCT_INFO" ,nullable = false)
    private String productInfo;

    @Column(name = "PRODUCT_STATUS" ,nullable = false)
    private String productStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "productDetails", cascade=CascadeType.ALL)
    @JsonIgnoreProperties("productDetails")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<OrderInfo> orders= new HashSet<OrderInfo>();

    public ProductDetails() {
    }

    public ProductDetails(String productName, Integer stockAvailble, String productInfo, String productStatus) {
        this.productName = productName;
        this.stockAvailble = stockAvailble;
        this.productInfo = productInfo;
        this.productStatus= productStatus;
    }

    public ProductDetails(Long productId, Set<OrderInfo> orders) {
        this.productId = productId;
        this.orders = orders;
    }

    public ProductDetails(Long productId, String productName, Integer stockAvailble, String productInfo,  String productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.stockAvailble = stockAvailble;
        this.productInfo = productInfo;
        this.productStatus= productStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStockAvailble() {
        return stockAvailble;
    }

    public void setStockAvailble(Integer stockAvailble) {
        this.stockAvailble = stockAvailble;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public Set<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderInfo> orders) {
        this.orders = orders;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
}
