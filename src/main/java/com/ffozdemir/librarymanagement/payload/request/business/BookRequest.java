package com.ffozdemir.librarymanagement.payload.request.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {

    @NotBlank(message = "Please enter book name")
    private String name;

    @NotBlank(message = "Please enter book ISBN")
    @Pattern(regexp = "^\\d{3}-\\d{7}-\\d{2}-\\d$", message = "ISBN format must be like '999-9999999-99-9'")
    private String isbn;

    @Positive(message = "Page count must be positive")
    @NotNull(message = "Please enter page count")
    private Integer pageCount;

    @NotNull(message = "Please enter author id")
    private Long authorId;

    @NotNull(message = "Please enter publisher id")
    private Long publisherId;

    @NotBlank(message = "Please enter publish date")
    @Pattern(regexp = "^\\d{4}$", message = "Publish date must be a 4-digit year")
    private String publishDate;

    @NotNull(message = "Please enter a category id")
    private Long categoryId;

    @NotBlank(message = "Please enter shelf code")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$", message = "Shelf code format must be like 'XX-000' (e.g. WF-214)")
    private String shelfCode;

    @NotNull(message = "Please enter featured status")
    private Boolean featured;

}
