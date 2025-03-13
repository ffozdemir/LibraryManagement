package com.ffozdemir.librarymanagement.controller.business;

import com.ffozdemir.librarymanagement.payload.response.business.ReportResponse;
import com.ffozdemir.librarymanagement.service.business.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @RequestMapping("/report")
    @GetMapping("")
    public ResponseEntity<ReportResponse> getReport() {
        return ResponseEntity.ok(reportService.getReport());
    }

}
