package com.ffozdemir.librarymanagement.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public abstract class AbstractUserRequest {

    @NotBlank(message = "First name cannot be null")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastName;

    @NotBlank(message = "Address cannot be null")
    @Size(min = 8, max = 100, message = "Address must be between 8 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-]+$", message = "Address must contain only letters, numbers, spaces, commas, periods, and hyphens")
    private String address;

    @NotBlank(message = "Phone number cannot be null")
    @Size(min = 8, max = 20, message = "Phone number must be between 8 and 20 characters")
    @Pattern(regexp = "^[0-9\\s-]+$", message = "Phone number must contain only numbers, spaces, and hyphens")
    private String phone;

    @NotNull(message = "Birth date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Email cannot be null")
    @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email should be valid")
    private String email;
}
