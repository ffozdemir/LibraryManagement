package com.ffozdemir.librarymanagement.controller.business;

import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import com.ffozdemir.librarymanagement.payload.response.business.ReportResponse;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.service.business.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/report")
    public ResponseEntity<ReportResponse> getReport() {
        return ResponseEntity.ok(reportService.getReport());
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/report/most-popular-books")
    public ResponseEntity<Page<BookResponse>> getMostPopularBooks(@RequestParam int amount,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(reportService.getMostPopularBooks(amount, page, size));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/report/unreturned-books")
    public ResponseEntity<Page<BookResponse>> getUnreturnedBooks(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "20") int size,
                                                                 @RequestParam(defaultValue = "expireDate") String sort,
                                                                 @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(reportService.getUnreturnedBooks(page, size, sort, direction));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/report/expired-books")
    public ResponseEntity<Page<BookResponse>> getExpiredBooks(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "20") int size,
                                                              @RequestParam(defaultValue = "expireDate") String sort,
                                                              @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(reportService.getExpiredBooks(page, size, sort, direction));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/report/most-borrowers")
    public ResponseEntity<Page<UserResponse>> getMostBorrowers(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(reportService.getMostBorrowers(page, size));
    }


}
