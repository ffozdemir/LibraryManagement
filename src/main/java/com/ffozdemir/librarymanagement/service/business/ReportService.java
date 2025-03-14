package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import com.ffozdemir.librarymanagement.payload.response.business.ReportResponse;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.service.helper.ReportHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportHelper reportHelper;

    public ReportResponse getReport() {
        return ReportResponse.builder()
                .totalBooks(reportHelper.getTotalBooks())
                .totalAuthors(reportHelper.getTotalAuthors())
                .totalPublishers(reportHelper.getTotalPublishers())
                .totalCategories(reportHelper.getTotalCategories())
                .totalLoans(reportHelper.getTotalLoans())
                .unReturnedBooks(reportHelper.getUnreturnedBooks())
                .expiredBooks(reportHelper.getExpiredBooks())
                .totalMembers(reportHelper.getTotalMembers())
                .build();
    }


    public Page<BookResponse> getMostPopularBooks(int amount, int page, int size) {
        return reportHelper.getMostPopularBooks(amount, page, size);
    }

    public Page<BookResponse> getUnreturnedBooks(int page, int size, String sort, String direction) {
        return reportHelper.getUnreturnedBooksPage(page, size, sort, direction);
    }

    public Page<BookResponse> getExpiredBooks(int page, int size, String sort, String direction) {
        return reportHelper.getExpiredBooksPage(page, size, sort, direction);
    }

    public Page<UserResponse> getMostBorrowers(int page, int size) {
        return reportHelper.getMostBorrowers(page, size);
    }
}
