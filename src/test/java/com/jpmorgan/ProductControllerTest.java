package com.jpmorgan;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmorgan.model.ProductDetails;
import com.jpmorgan.repository.CustomerRepository;
import com.jpmorgan.repository.OrderRepository;
import com.jpmorgan.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository mockCustomerRepository;

    @MockBean
    private OrderRepository mockOrderRepository;

    @MockBean
    private ProductRepository mockOProductRepository;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
         objectMapper = new ObjectMapper();
        ProductDetails productDetails =new ProductDetails("Suzuki Swift", 5,"Suzuki Car Model S  Make 2019", "ACTIVE");
        when(mockOProductRepository.findAllAvailbleProducts()).thenReturn(Arrays.asList(productDetails));
    }

    @WithMockUser("atul")
    @Test
    public void test_OKCase() throws Exception {

        MvcResult result = mockMvc.perform(get("/products/available"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();

        List<ProductDetails> myObjects = objectMapper.readValue(content, new TypeReference<List<ProductDetails>>(){});
        Assert.assertTrue(!myObjects.isEmpty());
        Assert.assertTrue(myObjects.get(0).getStockAvailble() ==5);

    }

    @Test
    public void noCredential_401() throws Exception {
        mockMvc.perform(get("/products/availalble"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
