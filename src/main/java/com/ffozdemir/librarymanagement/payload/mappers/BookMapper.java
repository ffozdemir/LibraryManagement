package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.business.Book;
import com.ffozdemir.librarymanagement.exception.BadRequestException;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.business.BookRequest;
import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import com.ffozdemir.librarymanagement.service.helper.MethodHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final PublisherMapper publisherMapper;
    private final CategoryMapper categoryMapper;
    private final MethodHelper methodHelper;

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

    public Book updateBookFromRequest(@Valid BookRequest bookRequest, Book book) {
        int publishDate;
        try {
            publishDate = Integer.parseInt(bookRequest.getPublishDate());
        } catch (NumberFormatException e) {
            throw new BadRequestException(ErrorMessages.INVALID_PUBLISH_DATE);
        }
        return Book.builder()
                .id(book.getId())
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .author(bookRequest.getAuthorId() != null ? methodHelper.getAuthorById(bookRequest.getAuthorId()) : book.getAuthor())
                .publisher(bookRequest.getPublisherId() != null ? methodHelper.getPublisherById(bookRequest.getPublisherId()) : book.getPublisher())
                .category(bookRequest.getCategoryId() != null ? methodHelper.getCategoryById(bookRequest.getCategoryId()) : book.getCategory())
                .loanable(book.isLoanable())
                .shelfCode(bookRequest.getShelfCode())
                .active(book.isActive())
                .featured(bookRequest.getFeatured())
                .createDate(book.getCreateDate())
                .publishDate(publishDate)
                .builtIn(book.isBuiltIn())
                .build();
    }
}
