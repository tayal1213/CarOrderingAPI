package com.jpmorgan;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmorgan.error.ProductNotAvaialbleException;
import com.jpmorgan.model.CustomerInfo;
import com.jpmorgan.model.ProductBuyingRequest;
import com.jpmorgan.model.ProductDetails;
import com.jpmorgan.repository.CustomerRepository;
import com.jpmorgan.repository.OrderRepository;
import com.jpmorgan.repository.ProductRepository;
import org.hibernate.criterion.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();


    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CustomerRepository mockRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductRepository productRepository;

    @Before
    public void init() {
        ProductDetails productDetails = new ProductDetails("Suzuki Swift", 2, "Suzuki Car Model Swift  Make 2019", "ACTIVE");
        ProductDetails productDetailsBMW = new ProductDetails("BMW", 2, "Suzuki Car Model Swift  Make 2019", "INACTIVE");
        when(productRepository.findAllAvailbleProducts()).thenReturn(Arrays.asList(productDetails));
        when(productRepository.findByProductName("Suzuki Swift")).thenReturn(productDetails);
        when(productRepository.findByProductName("BMW")).thenReturn(null);
        when(mockRepository.findByUsername(anyString())).thenReturn(new CustomerInfo("","","",new Date()));
    }

    @Test
    public void test_all_product_user_role() throws Exception {


        ResponseEntity<String> response = restTemplate
                .withBasicAuth("atul", "password")
                .getForEntity("/products/available", String.class);

        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Suzuki Swift"));


    }

    @Test
    public void test_no_credentials_Unauthorized() throws Exception {

        ResponseEntity<String> response = restTemplate
                .getForEntity("/products/available", String.class);

        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        assertTrue(response.getBody().contains("Unauthorized"));

    }

    @Test
    public void test_userNotAllowedToAddProducts() throws Exception {
        ProductDetails productDetails =new ProductDetails("Suzuki Swift", 5,"Suzuki Car Model S  Make 2019", "ACTIVE");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ProductDetails> request = new HttpEntity<>(productDetails, headers);
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("atul", "password")
                .postForEntity("/products/add", request,String.class);

        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        assertTrue(response.getBody().contains("Forbidden"));

    }

    @Test
    public void test_AddProducts() throws Exception {
        ProductDetails productDetails =new ProductDetails("Suzuki Swift", 5,"Suzuki Car Model S  Make 2019", "ACTIVE");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ProductDetails> request = new HttpEntity<>(productDetails, headers);
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("jpmorgan", "password")
                .postForEntity("/products/add", request,String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_buyProducts_NOTOK() throws Exception {
        ProductBuyingRequest buyingRequest =new ProductBuyingRequest("Suzuki Swift", 7);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ProductBuyingRequest> request = new HttpEntity<>(buyingRequest, headers);
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("atul", "password")
                .postForEntity("/product/buy", request,String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_buyProducts_OK() throws Exception {
        ProductBuyingRequest buyingRequest =new ProductBuyingRequest("Suzuki Swift", 2);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ProductBuyingRequest> request = new HttpEntity<>(buyingRequest, headers);
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("atul", "password")
                .postForEntity("/product/buy", request,String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_buyProducts_NOK() throws Exception {
        ProductBuyingRequest buyingRequest =new ProductBuyingRequest("BMW", 1);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ProductBuyingRequest> request = new HttpEntity<>(buyingRequest, headers);
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("atul", "password")
                .postForEntity("/product/buy", request,String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
