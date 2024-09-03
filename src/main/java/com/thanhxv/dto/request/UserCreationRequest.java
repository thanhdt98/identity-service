package com.thanhxv.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Size;

import com.thanhxv.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    /**
     * @FieldDefaults(level = AccessLevel.PRIVATE)
     * mac dinh cac field la private neu khong define
     */
    @Size(min = 4, message = "USERNAME_INVALID")
    String username;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;

    String firstName;
    String lastName;

    @DobConstraint(min = 2, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;
}
