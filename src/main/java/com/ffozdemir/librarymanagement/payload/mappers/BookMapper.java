package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.business.Book;
import com.ffozdemir.librarymanagement.payload.request.business.BookRequest;
import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final PublisherMapper publisherMapper;
    private final CategoryMapper categoryMapper;

    public Book bookRequestToBook(BookRequest bookRequest) {
        return Book.builder()
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .publishDate(Integer.parseInt(bookRequest.getPublishDate()))
                .shelfCode(bookRequest.getShelfCode())
                .featured(bookRequest.getFeatured())
                .build();
    }

    public BookResponse bookToBookResponse(Book book) {
        return BookResponse.builder()
                .name(book.getName())
                .isbn(book.getIsbn())
                .pageCount(book.getPageCount())
                .authorResponse(authorMapper.authorToAuthorResponse(book.getAuthor()))
                .publisherResponse(publisherMapper.publisherToPublisherResponse(book.getPublisher()))
                .publishDate(book.getPublishDate())
                .categoryResponse(categoryMapper.categoryToCategoryResponse(book.getCategory()))
                .loanable(book.isLoanable())
                .shelfCode(book.getShelfCode())
                .active(book.isActive())
                .featured(book.isFeatured())
                .createDate(book.getCreateDate())
                .build();
    }
}
