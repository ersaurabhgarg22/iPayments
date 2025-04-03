package com.db.iPayments.controller;

import com.db.iPayments.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@SpringBootTest
public class PaymentReceiverControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {
        // Initialize MockMvc and ObjectMapper
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateResource() throws Exception {

        String resourceJson = objectMapper.writeValueAsString("payment");

        // Perform the POST request to the API with the request body
        mockMvc.perform(post("/payment/process")
                        .contentType(MediaType.APPLICATION_JSON)  // Set Content-Type to JSON
                        .content(resourceJson))  // Pass the request body
                .andExpect(status().isOk())  // Expect HTTP 200 OK status
                .andExpect(content().string("Resource created: New Resource"));  // Validate response body
    }
}
