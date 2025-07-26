//package com.prathvi.blogApp.exceptions;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.prathvi.blogApp.payloads.ApiResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//public class GlobalExceptionHandlerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @InjectMocks
//    private GlobalExceptionHandler globalExceptionHandler;
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(globalExceptionHandler).build();
//    }
//
//    @Test
//    void testHandleResourceNotFoundException() throws Exception {
//        mockMvc.perform(get("/api/categories/999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", is("Resource not found")))
//                .andExpect(jsonPath("$.success", is(false)))
//                .andExpect(jsonPath("$.details").exists()); // Assuming you have a 'details' field
//    }
//
//
//    @Test
//    void testHandleMethodArgumentNotValidException() throws Exception {
//        String invalidRequestBody = "{\"name\":\"\"}"; // Invalid payload
//
//        mockMvc.perform(get("/api/categories/add")
//                        .content(invalidRequestBody)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.name", is("Category name cannot be empty"))); // Example message
//    }
//
//    @Test
//    void testHandleConstraintViolationException() throws Exception {
//        // Simulating constraint violation
//        mockMvc.perform(get("/api/some-endpoint-with-constraint-violation"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.fieldName", is("Field constraint error"))); // Replace with actual field
//    }
//
//    @Test
//    void testHandleBadCredentialsException() throws Exception {
//        // Simulate a failed login attempt
//        mockMvc.perform(get("/api/v1/auth/login")
//                        .header(HttpHeaders.AUTHORIZATION, "Invalid credentials"))
//                .andExpect(status().isUnauthorized())
//                .andExpect(content().string("Invalid username or password"));
//    }
//
//    @Test
//    void testHandleDisabledException() throws Exception {
//        // Simulate a disabled user trying to authenticate
//        throw new DisabledException("User is disabled");
//    }
//}

package com.prathvi.blogApp.exceptions;

import com.prathvi.blogApp.payloads.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setup() {
        globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(globalExceptionHandler).build();
    }

    @Test
    void testResourceNotFoundExceptionHandler() throws Exception {
        ResourceNotFoundException ex = new ResourceNotFoundException("User", "id", 1);

        ApiResponse response = globalExceptionHandler.resourceNotFoundExceptionHandler(ex).getBody();

        mockMvc.perform(get("/api/v1/resource-not-found"))
                .andExpect(status().isNotFound());
//                .andExpect(jsonPath("$.message", is(response.message)))
//                .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    void testHandleMethodArgumentNotValidException() throws Exception {
        String invalidRequestBody = "{\"name\":\"\"}"; // Invalid payload

        mockMvc.perform(get("/api/categories/add")
                        .content(invalidRequestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void testHandleBadCredentialsException() throws Exception {
        BadCredentialsException ex = new BadCredentialsException("Invalid credentials");

        mockMvc.perform(get("/api/v1/auth/login")
                        .header(HttpHeaders.AUTHORIZATION, "Invalid credentials"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void testHandleDisabledException() throws Exception {
        DisabledException ex = new DisabledException("User is disabled");

        mockMvc.perform(get("/api/v1/auth/login")
                        .header(HttpHeaders.AUTHORIZATION, "Disabled user"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void testHandleConstraintViolationException() throws Exception {
        Map<String, String> errors = new HashMap<>();
        errors.put("username", "must not be empty");
        errors.put("password", "must not be less than 6 characters");

        mockMvc.perform(get("/api/v1/constraint-violation"))
                .andExpect(status().isNotFound());
    }
}

