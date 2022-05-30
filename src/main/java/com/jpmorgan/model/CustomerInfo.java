package com.jpmorgan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "CUSTOMER_INFO", uniqueConstraints = {
        @UniqueConstraint(columnNames = "CUSTOMER_ID"),
        @UniqueConstraint(columnNames = "EMAIL_ADDRESS")})
public class CustomerInfo  {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CUSTOMER_ID", unique = true, nullable = false)
    private Long customerId;

    @Column(name = "NAME" ,nullable = false)
    @NotEmpty(message = "Please provide a name")
    private String name;

    @Column(name = "ADDRESS" ,nullable = false)
    @NotEmpty(message = "Please provide address")
    private String address;

    @Column(name = "EMAIL_ADDRESS" ,nullable = false)
    @NotEmpty(message = "Please provide a emailAddress")
    private String emailAddress;


    @Column(name = "CREATED_TS" ,nullable = false)
    private Date created_ts;



    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customerInfo", cascade=CascadeType.ALL)
    @JsonIgnoreProperties("customerInfo")
    private Set<OrderInfo> orders= new HashSet<OrderInfo>();


    public CustomerInfo() {

    }

    public CustomerInfo(Long customerId, String name, String address, String emailAddress, Set<OrderInfo> orders) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
        this.orders = orders;
    }
    public CustomerInfo(String name, String address, String emailAddress, Date date) {
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
        this.created_ts =date;
    }

    public CustomerInfo(Long customerId, String name, String address, String emailAddress, Date created_ts) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
        this.created_ts = created_ts;
    }


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderInfo> orders) {
        this.orders = orders;
    }

    public Date getCreated_ts() {
        return created_ts;
    }

    public void setCreated_ts(Date created_ts) {
        this.created_ts = created_ts;
    }


    @Override
    public String toString() {
        return "CustomerInfo{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }

}
