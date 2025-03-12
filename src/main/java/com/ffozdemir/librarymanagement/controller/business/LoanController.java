package com.ffozdemir.librarymanagement.controller.business;

import com.ffozdemir.librarymanagement.payload.response.business.LoanResponse;
import com.ffozdemir.librarymanagement.service.business.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PreAuthorize("hasAuthority('Member')")
    @GetMapping("/loans/{id}")
    public ResponseEntity<LoanResponse> getLoanResponseForAuthMember(HttpServletRequest httpServletRequest,
                                                                     @PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanResponseForAuthMember(httpServletRequest, id));
    }

    @PreAuthorize("hasAuthority('Member')")
    @GetMapping("/loans")
    public ResponseEntity<Page<LoanResponse>> getAllLoanResponseForAuthMember(HttpServletRequest httpServletRequest,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size,
                                                                              @RequestParam(defaultValue = "id") String sort,
                                                                              @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(loanService.getAllLoanResponseForAuthMember(httpServletRequest, page, size, sort, direction));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/loans/user/{id}")
    public ResponseEntity<Page<LoanResponse>> getAllLoanResponseByUserId(@PathVariable Long id,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size,
                                                                         @RequestParam(defaultValue = "id") String sort,
                                                                         @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(loanService.getAllLoanResponseByUserId(id, page, size, sort, direction));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/loans/book/{id}")
    public ResponseEntity<Page<LoanResponse>> getAllLoanResponseByBookId(@PathVariable Long id,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size,
                                                                         @RequestParam(defaultValue = "id") String sort,
                                                                         @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(loanService.getAllLoanResponseByBookId(id, page, size, sort, direction));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/loans/auth/{id}")
    public ResponseEntity<LoanResponse> getLoanResponseByLoanId(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanResponseByLoanId(id));
    }

}
