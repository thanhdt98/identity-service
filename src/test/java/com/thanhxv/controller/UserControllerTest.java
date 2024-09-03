package com.thanhxv.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thanhxv.dto.request.UserCreationRequest;
import com.thanhxv.dto.response.UserResponse;
import com.thanhxv.service.UserService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
/**
 * tao mock request den controller
 */
@AutoConfigureMockMvc
/**
 * khi chay UnitTest can config de khong phu thuoc vao code hien tai
 */
@TestPropertySource("/test.properties")
public class UserControllerTest {

    /**
     * mockMvc se goi goi api
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * mock bean
     */
    @MockBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;

    /**
     * @BeforeEach chay truoc moi test case (@Test)
     */
    @BeforeEach
    void initData() {
        request = UserCreationRequest.builder()
                .username("bravof")
                .firstName("bravof")
                .lastName("bravof")
                .password("12345678")
                .dob(LocalDate.of(1990, 1, 1))
                .build();

        userResponse = UserResponse.builder()
                .id("1111111111")
                .username("bravof")
                .firstName("bravof")
                .lastName("bravof")
                .dob(LocalDate.of(1990, 1, 1))
                .build();
    }

    @Test
    /**
     * description
     */
    void createUser_validRequest_success() throws Exception {
        /**
         * 1 test case gom 3 phan
         */
        // GIVEN
        // Du lieu dau vao da biet truoc va du doan xay ra nhu vay
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);

        // WHEN
        // Test cai gi
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));

        // THEN
        // Expect cai gi
    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        // GIVEN
        request.setUsername("leo");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);

        // WHEN THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 4 characters"));
    }
}
