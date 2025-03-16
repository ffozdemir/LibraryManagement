package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.business.Book;
import com.ffozdemir.librarymanagement.entity.concretes.business.Loan;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.payload.request.business.CreateLoanRequest;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponseForAdminAndStaff;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponseForMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    public Loan createLoanRequestToLoan(@Valid CreateLoanRequest createLoanRequest, User user, Book book, Integer dayLimit) {
        return Loan.builder()
                .user(user)
                .book(book)
                .loanDate(LocalDateTime.now())
                .expireDate(LocalDateTime.now().plusDays(dayLimit))
                .notes(createLoanRequest.getNotes())
                .build();
    }
}
