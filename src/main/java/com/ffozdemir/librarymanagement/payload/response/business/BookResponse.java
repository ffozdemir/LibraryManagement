package com.ffozdemir.librarymanagement.payload.response.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private String name;
    private String isbn;
    private int pageCount;
    private AuthorResponse authorResponse;
    private PublisherResponse publisherResponse;
    private int publishDate;
    private CategoryResponse categoryResponse;
    private boolean loanable;
    private String shelfCode;
    private boolean active;
    private boolean featured;
    private LocalDateTime createDate;
}
