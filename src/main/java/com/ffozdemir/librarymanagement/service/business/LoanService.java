package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Book;
import com.ffozdemir.librarymanagement.entity.concretes.business.Loan;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.exception.ForbiddenException;
import com.ffozdemir.librarymanagement.payload.mappers.LoanMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.business.CreateLoanRequest;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponseForAdminAndStaff;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponseForMember;
import com.ffozdemir.librarymanagement.repository.business.LoanRepository;
import com.ffozdemir.librarymanagement.service.helper.MethodHelper;
import com.ffozdemir.librarymanagement.service.helper.PageableHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    public LoanResponseForMember getLoanResponseForAuthMember(HttpServletRequest httpServletRequest, Long id) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authUser = methodHelper.loadUserByEmail(email);
        Loan loan = getLoanIfLoanBelongsToUser(id, authUser);
        return loanMapper.loanToLoanResponse(loan);
    }

    public Loan getLoanIfLoanBelongsToUser(Long loanId, User authUser) {
        return loanRepository.findLoanByIdAndUser_Id(loanId, authUser.getId()).orElseThrow(() -> new ForbiddenException(String.format(ErrorMessages.LOAN_NOT_BELONG_TO_USER, loanId)));
    }

    public Page<LoanResponseForMember> getAllLoanResponseForAuthMember(HttpServletRequest httpServletRequest, int page, int size, String sort, String direction) {
        String email = (String) httpServletRequest.getAttribute("email");
        User authUser = methodHelper.loadUserByEmail(email);
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Loan> loans = loanRepository.findAllByUser_Id(authUser.getId(), pageable);
        return loans.map(loanMapper::loanToLoanResponse);
    }

    public Page<LoanResponseForAdminAndStaff> getAllLoanResponseForAdminAndStaffByUserId(Long id, int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Loan> loans = loanRepository.findAllByUser_Id(id, pageable);
        return loans.map(loanMapper::loanToLoanResponseForAdminAndStaff);
    }


    public Page<LoanResponseForAdminAndStaff> getAllLoanResponseForAdminAndStaffByBookId(Long id, int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Loan> loans = loanRepository.findAllByBook_Id(id, pageable);
        return loans.map(loanMapper::loanToLoanResponseForAdminAndStaff);
    }

    public LoanResponseForAdminAndStaff getLoanResponseForAdminAndStaffByLoanId(Long id) {
        Loan loan = methodHelper.getLoanById(id);
        return loanMapper.loanToLoanResponseForAdminAndStaff(loan);
    }

    @Transactional
    public LoanResponseForAdminAndStaff createLoan(@Valid CreateLoanRequest createLoanRequest) {
        Long userId = createLoanRequest.getUserId();
        Long bookId = createLoanRequest.getBookId();
        User user = methodHelper.isUserExistById(userId);
        Book book = methodHelper.getBookIfAvailable(bookId);
        validateLoanEligibility(user);
        Map<String, Integer> borrowLimits = methodHelper.getUserBorrowLimits(user);
        Loan loanToSave = loanMapper.createLoanRequestToLoan(createLoanRequest, user, book, borrowLimits.get("dayLimit"));
        book.setLoanable(false);
        Loan savedLoan = loanRepository.save(loanToSave);
        return loanMapper.loanToLoanResponseForAdminAndStaff(savedLoan);
    }

    private void validateLoanEligibility(User user) {
        Long userId = user.getId();
        methodHelper.checkIfUserHasAnyExpiredLoan(userId);
        Map<String, Integer> borrowLimits = methodHelper.getUserBorrowLimits(user);
        List<Loan> activeLoans = loanRepository.findAllByUser_IdAndReturnDateIsNull(userId);
        if (activeLoans.size() >= borrowLimits.get("bookLimit")) {
            throw new ForbiddenException(ErrorMessages.USER_BORROW_LIMIT_EXCEEDED);
        }
    }
}
