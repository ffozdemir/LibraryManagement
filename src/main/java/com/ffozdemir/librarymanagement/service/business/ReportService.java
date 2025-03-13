package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.payload.response.business.ReportResponse;
import com.ffozdemir.librarymanagement.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final MethodHelper methodHelper;

    public ReportResponse getReport() {
        return ReportResponse.builder()
                .totalBooks(methodHelper.getTotalBooks())
                .totalAuthors(methodHelper.getTotalAuthors())
                .totalPublishers(methodHelper.getTotalPublishers())
                .totalCategories(methodHelper.getTotalCategories())
                .totalLoans(methodHelper.getTotalLoans())
                .unReturnedBooks(methodHelper.getUnreturnedBooks())
                .expiredBooks(methodHelper.getExpiredBooks())
                .totalMembers(methodHelper.getTotalMembers())
                .build();
    }
}
