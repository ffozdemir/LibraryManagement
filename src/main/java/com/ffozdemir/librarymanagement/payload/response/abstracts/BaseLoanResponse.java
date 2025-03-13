package com.ffozdemir.librarymanagement.payload.response.abstracts;

import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseLoanResponse {
    private Long userId;
    private Long bookId;
    private BookResponse bookResponse;
    private LocalDateTime loanDate;
    private LocalDateTime expireDate;
    private LocalDateTime returnDate;
}
