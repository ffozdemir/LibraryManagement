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
public class PublisherRequest {
    @NotBlank(message = "Please provide a publisher name")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Publisher name can only contain letters, numbers, and spaces")
    private String name;
}
