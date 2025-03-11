package com.ffozdemir.librarymanagement.payload.response.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Author;
import com.ffozdemir.librarymanagement.entity.concretes.business.Category;
import com.ffozdemir.librarymanagement.entity.concretes.business.Publisher;
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
    private Author author;
    private Publisher publisher;
    private int publishDate;
    private Category category;
    private boolean loanable;
    private String shelfCode;
    private boolean active;
    private boolean featured;
    private LocalDateTime createDate;
}
