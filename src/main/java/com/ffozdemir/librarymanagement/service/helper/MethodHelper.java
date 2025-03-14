package com.ffozdemir.librarymanagement.service.helper;

import com.ffozdemir.librarymanagement.entity.concretes.business.*;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.mappers.BookMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import com.ffozdemir.librarymanagement.repository.business.*;
import com.ffozdemir.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MethodHelper {
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final PageableHelper pageableHelper;
    private final BookMapper bookMapper;

    public User loadUserByEmail(
            String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_BY_EMAIL, email)));
    }

    public User isUserExistById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_BY_ID, id)));
    }

    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.AUTHOR_NOT_FOUND_BY_ID, authorId)));
    }

    public Publisher getPublisherById(Long publisherId) {
        return publisherRepository.findById(publisherId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PUBLISHER_NOT_FOUND_BY_ID,
                        publisherId)));
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND_BY_ID, categoryId)));
    }

    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.LOAN_NOT_FOUND_BY_ID, loanId)));
    }

    public boolean isBookRelatedToLoan(Long bookId) {
        return loanRepository.existsByBook_Id((bookId));
    }

    public Book getBookIfAvailable(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND_BY_ID, bookId)));
        if (!book.isActive() || !book.isLoanable()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.BOOK_NOT_AVAILABLE, bookId));
        }
        return book;
    }

    public void checkIfUserHasAnyExpiredLoan(Long userId) {
        if (loanRepository.existsByUser_IdAndReturnDateIsNullAndExpireDateBefore(userId, LocalDateTime.now())) {
            throw new ResourceNotFoundException(ErrorMessages.USER_HAS_LOAN_AND_NOT_RETURNED_BOOK);
        }
    }

    public Map<String, Integer> getUserBorrowLimits(User user) {
        int bookLimit;
        int dayLimit;
        int score = user.getScore();
        if (score < -1) {
            bookLimit = 1;
            dayLimit = 3;
        } else if (score < 0) {
            bookLimit = 2;
            dayLimit = 6;
        } else if (score < 1) {
            bookLimit = 3;
            dayLimit = 10;
        } else if (score < 2) {
            bookLimit = 4;
            dayLimit = 15;
        } else {
            bookLimit = 5;
            dayLimit = 20;
        }
        return Map.of("bookLimit", bookLimit, "dayLimit", dayLimit);
    }


    public Long getTotalBooks() {
        return bookRepository.count();
    }

    public Long getTotalAuthors() {
        return authorRepository.count();
    }

    public Long getTotalPublishers() {
        return publisherRepository.count();
    }

    public Long getTotalCategories() {
        return categoryRepository.count();
    }

    public Long getTotalLoans() {
        return loanRepository.count();
    }

    public Long getUnreturnedBooks() {
        return loanRepository.countByReturnDateIsNull();
    }

    public Long getExpiredBooks() {
        return loanRepository.countByExpireDateBefore(LocalDateTime.now());
    }

    public Long getTotalMembers() {
        return userRepository.countByRole_RoleType(RoleType.MEMBER);
    }


    public Page<BookResponse> getMostPopularBooks(int amount, int page, int size) {
        Pageable pageable = pageableHelper.getPageable(page, size, "loanCount", "desc");
        Page<Book> books = bookRepository.findMostBorrowedBooks(amount, pageable);
        return books.map(bookMapper::bookToBookResponse);
    }

    public Page<BookResponse> getUnreturnedBooksPage(int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Book> books = bookRepository.findAllByLoans_ReturnDateIsNull(pageable);
        return books.map(bookMapper::bookToBookResponse);
    }

    public Page<BookResponse> getExpiredBooksPage(int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Book> books = bookRepository.findAllByExpireDateBeforeAndNotReturned(LocalDateTime.now(), pageable);
        return books.map(bookMapper::bookToBookResponse);
    }
}
