package com.ffozdemir.librarymanagement.payload.response.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserResponse {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private LocalDate birthDate;
    private String email;
}
