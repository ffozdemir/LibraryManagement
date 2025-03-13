package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.business.Loan;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponseForAdminAndStaff;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponseForMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanMapper {
    private final BookMapper bookMapper;

    public LoanResponseForMember loanToLoanResponse(Loan loan) {
        return LoanResponseForMember.builder()
                .userId(loan.getUser().getId())
                .bookId(loan.getBook().getId())
                .bookResponse(loan.getBook() != null ? bookMapper.bookToBookResponse(loan.getBook()) : null)
                .loanDate(loan.getLoanDate())
                .expireDate(loan.getExpireDate())
                .returnDate(loan.getReturnDate())
                .build();
    }

    public LoanResponseForAdminAndStaff loanToLoanResponseForAdminAndStaff(Loan loan) {
        return LoanResponseForAdminAndStaff.builder()
                .userId(loan.getUser().getId())
                .bookId(loan.getBook().getId())
                .bookResponse(loan.getBook() != null ? bookMapper.bookToBookResponse(loan.getBook()) : null)
                .loanDate(loan.getLoanDate())
                .expireDate(loan.getExpireDate())
                .returnDate(loan.getReturnDate())
                .notes(loan.getNotes())
                .build();
    }

}
