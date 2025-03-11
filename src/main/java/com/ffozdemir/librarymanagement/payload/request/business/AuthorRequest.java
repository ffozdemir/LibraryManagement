package com.ffozdemir.librarymanagement.payload.request.business;

import jakarta.validation.constraints.NotBlank;
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
    private String name;
}
