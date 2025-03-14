package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import com.ffozdemir.librarymanagement.payload.response.business.ReportResponse;
import com.ffozdemir.librarymanagement.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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


    public Page<BookResponse> getMostPopularBooks(int amount, int page, int size) {
        return methodHelper.getMostPopularBooks(amount, page, size);
    }

    public Page<BookResponse> getUnreturnedBooks(int page, int size, String sort, String direction) {
        return methodHelper.getUnreturnedBooksPage(page, size, sort, direction);
    }
}
