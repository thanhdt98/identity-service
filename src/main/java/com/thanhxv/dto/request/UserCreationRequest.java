package com.thanhxv.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 6, message = "password min 6 characters")
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
