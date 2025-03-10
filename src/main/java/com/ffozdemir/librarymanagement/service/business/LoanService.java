package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.repository.business.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public boolean isUserHasLoan(Long id) {
        return loanRepository.existsByUser_Id(id);
    }
}
