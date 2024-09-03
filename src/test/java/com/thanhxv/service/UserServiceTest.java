package com.thanhxv.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.thanhxv.dto.request.UserCreationRequest;
import com.thanhxv.dto.response.UserResponse;
import com.thanhxv.entity.Role;
import com.thanhxv.entity.User;
import com.thanhxv.exception.AppException;
import com.thanhxv.repository.RoleRepository;
import com.thanhxv.repository.UserRepository;

@SpringBootTest
/**
 * khi chay UnitTest can config de khong phu thuoc vao code hien tai
 */
@TestPropertySource("/test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private Optional<Role> role;

    @BeforeEach
    void initData() {
        request = UserCreationRequest.builder()
                .username("bravo")
                .firstName("bravo")
                .lastName("bravo")
                .password("12345678")
                .dob(LocalDate.of(1990, 1, 1))
                .build();

        userResponse = UserResponse.builder()
                .id("07fb7f6ede1a")
                .username("bravo")
                .firstName("bravo")
                .lastName("bravo")
                .dob(LocalDate.of(1990, 1, 1))
                .build();

        user = User.builder()
                .id("07fb7f6ede1a")
                .username("bravo")
                .firstName("bravo")
                .lastName("bravo")
                .dob(LocalDate.of(1990, 1, 1))
                .build();

        role = Optional.of(
                Role.builder().name(com.thanhxv.enums.Role.USER.name()).build());
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(roleRepository.findById(anyString())).thenReturn(role);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createUser(request);

        // THEN
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo("07fb7f6ede1a");
        Assertions.assertThat(response.getUsername()).isEqualTo("bravo");
        Assertions.assertThat(response.getFirstName()).isEqualTo("bravo");
        Assertions.assertThat(response.getLastName()).isEqualTo("bravo");
        Assertions.assertThat(response.getDob()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "bravo")
    /**
     * explain => add dependency spring-security-test
     * dung @WithMockUser(username = "bravo") vi trong getMyInfo(); co dung SecurityContextHolder.getContext(); de lay thong tin logged in
     * co the mock ca role
     */
    void getMyInfo_valid_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("bravo");
        Assertions.assertThat(response.getId()).isEqualTo("07fb7f6ede1a");
    }

    @Test
    @WithMockUser(username = "bravo")
    void getMyInfo_userNotFound_error() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }
}
