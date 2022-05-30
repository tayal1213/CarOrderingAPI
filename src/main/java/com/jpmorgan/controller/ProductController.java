package com.jpmorgan.controller;


import com.jpmorgan.error.CustomerNotFoundException;
import com.jpmorgan.error.InventoryUpdateException;
import com.jpmorgan.error.ProductNotAvaialbleException;
import com.jpmorgan.model.CustomerInfo;
import com.jpmorgan.model.OrderInfo;
import com.jpmorgan.model.ProductBuyingRequest;
import com.jpmorgan.model.ProductDetails;
import com.jpmorgan.repository.CustomerRepository;
import com.jpmorgan.repository.OrderRepository;
import com.jpmorgan.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

@RestController

public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @RequestMapping(method = RequestMethod.GET,value = "products/available")
    public List<ProductDetails> getAllProducts() {
        logger.info("Fetching All the available Products");
        List<ProductDetails> productDetails = productRepository.findAllAvailbleProducts();
        return productDetails;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "products/remove/{id}")
    public void deleteProduct(@PathVariable Long id) {
        logger.info("Deleting Product with id {}", id);
        List<OrderInfo> orderInfos = orderRepository.findByProductId(id);
        if (!orderInfos.isEmpty()) {
            if (orderInfos.stream().filter(orderInfo -> orderInfo.getOrderStatus().
                    equalsIgnoreCase("PENDING")).collect(Collectors.toList()).isEmpty()) {
                productRepository.updateProductStatus(id, "INACTIVE");
            } else {
                throw new InventoryUpdateException();
            }

        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "products/add")
    public void addProduct(@RequestBody ProductDetails productdetail) {
        logger.info("Adding Product with name {}", productdetail.getProductName());
        ProductDetails pd = productRepository.findByProductName(productdetail.getProductName());
        if (pd != null) {
            pd.setStockAvailble(pd.getStockAvailble() + productdetail.getStockAvailble());
            productRepository.save(pd);

        } else {
            productRepository.save(productdetail);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "product/buy")
    public void buyProduct(@RequestBody ProductBuyingRequest buyingRequest, Principal principal) throws CustomerNotFoundException, ProductNotAvaialbleException {
        logger.info("Buyig Product with product Name and Quantity {} {}", buyingRequest.getProductName(), buyingRequest.getQuantity());
        ProductDetails pd = productRepository.findByProductName(buyingRequest.getProductName());
        if (pd != null && pd.getStockAvailble() >= buyingRequest.getQuantity()) {
            pd.setStockAvailble(pd.getStockAvailble() - buyingRequest.getQuantity());

            CustomerInfo customerInfo = customerRepository.findByUsername(principal.getName());
            if (customerInfo == null)
                throw new CustomerNotFoundException(principal.getName());
            productRepository.save(pd);
            orderRepository.save(new OrderInfo(buyingRequest.getQuantity(), new Date(), pd, customerInfo));


        } else {
            throw new ProductNotAvaialbleException(buyingRequest.getProductName());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orders")
    public List<OrderInfo> getAllOrders() {
        logger.info("Getting All Orders ");
        List<OrderInfo> productDetails = orderRepository.findAll();
        return productDetails;
    }

    @GetMapping("/customer")
    List<CustomerInfo> findAll() {
        logger.info("Getting All Customers with respective orders ");
        return customerRepository.findAll();
    }


}
