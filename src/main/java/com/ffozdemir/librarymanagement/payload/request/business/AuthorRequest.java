package com.ffozdemir.librarymanagement.payload.request.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorRequest {
    @NotBlank(message = "Please provide a author name")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Author name can only contain letters and spaces")
    private String name;
}
