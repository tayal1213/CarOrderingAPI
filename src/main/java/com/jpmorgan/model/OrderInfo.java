package com.jpmorgan.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "ORDER_INFO", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ORDER_ID")})
public class OrderInfo {



    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ORDER_ID", unique = true, nullable = false)
    private Long orderId;

    @Column(name = "QUANTITY" ,nullable = false)
    private Integer quantity;

    @Column(name = "CREATED_TS" ,nullable = false)
    private Date orderDate;

    @Column(name = "ORDER_STATUS")
    private String orderStatus;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("orders")
    @JoinColumn(name = "PRODUCT_ID")
    private ProductDetails productDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("orders")
    @JoinColumn(name = "Customer_ID")
    private CustomerInfo customerInfo;

    public OrderInfo(Integer quantity, Date orderDate, ProductDetails productDetails, CustomerInfo customerInfo, String orderStatus) {
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.productDetails = productDetails;
        this.customerInfo = customerInfo;
        this.orderStatus = orderStatus;
    }

    public OrderInfo() {

    }
    public OrderInfo(Integer quantity, Date orderDate, ProductDetails productDetails, CustomerInfo customerInfo) {
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.productDetails = productDetails;
        this.customerInfo = customerInfo;
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }


    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }



}
