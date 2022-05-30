package com.jpmorgan.repository;

import com.jpmorgan.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<CustomerInfo, Long> {

    @Query(value =  "SELECT new CustomerInfo (cu.customerId,cu.name, cu.address, cu.emailAddress, cu.created_ts) from CustomerInfo  cu where cu.name=?1 ")
    CustomerInfo findByUsername(String name);
}
