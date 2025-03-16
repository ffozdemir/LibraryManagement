package com.ffozdemir.librarymanagement.payload.request.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateLoanRequest {

    @NotNull(message = "Please enter user id")
    private Long userId;
    @NotNull(message = "Please enter book id")
    private Long bookId;
    @NotBlank(message = "Please enter notes")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Notes can only contain letters, numbers, and spaces")
    private String notes;

}
