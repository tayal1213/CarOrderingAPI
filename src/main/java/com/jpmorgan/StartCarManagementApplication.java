package com.jpmorgan;

import com.jpmorgan.model.CustomerInfo;
import com.jpmorgan.model.OrderInfo;
import com.jpmorgan.model.ProductDetails;
import com.jpmorgan.repository.CustomerRepository;
import com.jpmorgan.repository.OrderRepository;
import com.jpmorgan.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class StartCarManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartCarManagementApplication.class, args);
    }

    @Profile("demo")
    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository , OrderRepository orderRepository, ProductRepository productRepository) {
        return args -> {
            CustomerInfo customerInfo = new CustomerInfo("atul" , "Singapore", "atul.tayal@gamil.com" , new Date());
            ProductDetails productDetails = new ProductDetails("Suzuki Swift" , 5, "Suzuki Car Model Swift Make 2010","ACTIVE" );
            customerRepository.save( customerInfo);
            productRepository.save(productDetails);
        };
    }
}