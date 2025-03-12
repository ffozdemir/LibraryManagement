package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Loan;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.exception.ForbiddenException;
import com.ffozdemir.librarymanagement.payload.mappers.LoanMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponse;
import com.ffozdemir.librarymanagement.repository.business.LoanRepository;
import com.ffozdemir.librarymanagement.service.helper.MethodHelper;
import com.ffozdemir.librarymanagement.service.helper.PageableHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final MethodHelper methodHelper;
    private final LoanMapper loanMapper;
    private final PageableHelper pageableHelper;

    public boolean isUserHasLoan(Long id) {
        return loanRepository.existsByUser_Id(id);
    }

    public LoanResponse getLoanResponseForAuthMember(HttpServletRequest httpServletRequest, Long id) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authUser = methodHelper.loadUserByEmail(email);
        Loan loan = getLoanIfLoanBelongsToUser(id, authUser);
        return loanMapper.loanToLoanResponse(loan);
    }

    public Loan getLoanIfLoanBelongsToUser(Long loanId, User authUser) {
        return loanRepository.findLoanByIdAndUser_Id(loanId, authUser.getId())
                .orElseThrow(() -> new ForbiddenException(String.format(ErrorMessages.LOAN_NOT_BELONG_TO_USER, loanId)));
    }

    public Page<LoanResponse> getAllLoanResponseForAuthMember(HttpServletRequest httpServletRequest, int page, int size, String sort, String direction) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authUser = methodHelper.loadUserByEmail(email);
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Loan> loans = loanRepository.findAllByUser_Id(authUser.getId(), pageable);
        return loans.map(loanMapper::loanToLoanResponse);
    }

    public Page<LoanResponse> getAllLoanResponseByUserId(Long id, int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Loan> loans = loanRepository.findAllByUser_Id(id, pageable);
        return loans.map(loanMapper::loanToLoanResponse);
    }


    public Page<LoanResponse> getAllLoanResponseByBookId(Long id, int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Loan> loans = loanRepository.findAllByBook_Id(id, pageable);
        return loans.map(loanMapper::loanToLoanResponse);
    }

    public LoanResponse getLoanResponseByLoanId(Long id) {
        Loan loan = methodHelper.getLoanById(id);
        return loanMapper.loanToLoanResponse(loan);
    }
}
