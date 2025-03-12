package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.business.Loan;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanMapper {
    private final BookMapper bookMapper;

    public LoanResponse loanToLoanResponse(Loan loan) {
        return LoanResponse.builder()
                .userId(loan.getUser().getId())
                .bookId(loan.getBook().getId())
                .bookResponse(loan.getBook() != null ? bookMapper.bookToBookResponse(loan.getBook()) : null)
                .loanDate(loan.getLoanDate())
                .expireDate(loan.getExpireDate())
                .returnDate(loan.getReturnDate())
                .build();
    }
}
