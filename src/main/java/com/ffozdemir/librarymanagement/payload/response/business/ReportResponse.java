package com.ffozdemir.librarymanagement.payload.response.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {
    private Long totalBooks;
    private Long totalAuthors;
    private Long totalPublishers;
    private Long totalCategories;
    private Long totalLoans;
    private Long unReturnedBooks;
    private Long expiredBooks;
    private Long totalMembers;
}
