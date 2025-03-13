package com.ffozdemir.librarymanagement.controller.business;

import com.ffozdemir.librarymanagement.payload.request.business.CreateLoanRequest;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponseForAdminAndStaff;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponseForMember;
import com.ffozdemir.librarymanagement.service.business.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<LoanResponseForMember> getLoanResponseForAuthMember(HttpServletRequest httpServletRequest,
                                                                              @PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanResponseForAuthMember(httpServletRequest, id));
    }

    @PreAuthorize("hasAuthority('Member')")
    @GetMapping("/loans")
    public ResponseEntity<Page<LoanResponseForMember>> getAllLoanResponseForAuthMember(HttpServletRequest httpServletRequest,
                                                                                       @RequestParam(defaultValue = "0") int page,
                                                                                       @RequestParam(defaultValue = "10") int size,
                                                                                       @RequestParam(defaultValue = "id") String sort,
                                                                                       @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(loanService.getAllLoanResponseForAuthMember(httpServletRequest, page, size, sort, direction));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/loans/user/{id}")
    public ResponseEntity<Page<LoanResponseForAdminAndStaff>> getAllLoanResponseByUserId(@PathVariable Long id,
                                                                                         @RequestParam(defaultValue = "0") int page,
                                                                                         @RequestParam(defaultValue = "10") int size,
                                                                                         @RequestParam(defaultValue = "id") String sort,
                                                                                         @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(loanService.getAllLoanResponseForAdminAndStaffByUserId(id, page, size, sort, direction));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/loans/book/{id}")
    public ResponseEntity<Page<LoanResponseForAdminAndStaff>> getAllLoanResponseForAdminAndStaffByBookId(@PathVariable Long id,
                                                                                  @RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size,
                                                                                  @RequestParam(defaultValue = "id") String sort,
                                                                                  @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(loanService.getAllLoanResponseForAdminAndStaffByBookId(id, page, size, sort, direction));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/loans/auth/{id}")
    public ResponseEntity<LoanResponseForAdminAndStaff> getLoanResponseForAdminAndStaffByLoanId(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanResponseForAdminAndStaffByLoanId(id));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @PostMapping("/loans")
    public ResponseEntity<LoanResponseForAdminAndStaff> createLoan(@Valid @RequestBody CreateLoanRequest createLoanRequest) {
        return ResponseEntity.ok(loanService.createLoan(createLoanRequest));
    }

}
