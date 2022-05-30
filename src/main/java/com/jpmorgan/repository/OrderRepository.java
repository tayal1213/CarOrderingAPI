package com.jpmorgan.repository;

import com.jpmorgan.model.OrderInfo;
import com.jpmorgan.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderInfo, Long> {

    @Query(value =  "SELECT new OrderInfo(od.quantity, od.orderDate, od.productDetails,od.customerInfo,od.orderStatus) from OrderInfo  od where od.productDetails.productId =:id")
    List<OrderInfo> findByProductId(@Param("id") Long id);
}
