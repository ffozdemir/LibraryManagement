package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Author;
import com.ffozdemir.librarymanagement.entity.concretes.business.Book;
import com.ffozdemir.librarymanagement.entity.concretes.business.Category;
import com.ffozdemir.librarymanagement.entity.concretes.business.Publisher;
import com.ffozdemir.librarymanagement.exception.ConflictException;
import com.ffozdemir.librarymanagement.payload.mappers.BookMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.business.BookRequest;
import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import com.ffozdemir.librarymanagement.repository.business.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;

    public BookResponse createBook(BookRequest bookRequest) {
        validateBookIsbn(bookRequest.getIsbn());
        Author author = authorService.getAuthorById(bookRequest.getAuthorId());
        Publisher publisher = publisherService.getPublisherById(bookRequest.getPublisherId());
        Category category = categoryService.isCategoryExists(bookRequest.getCategoryId());
        Book book = bookMapper.bookRequestToBook(bookRequest);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);
        return bookMapper.bookToBookResponse(bookRepository.save(book));
    }

    private void validateBookIsbn(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new ConflictException(String.format(ErrorMessages.BOOK_ISBN_ALREADY_EXISTS, isbn));
        }
    }
}
