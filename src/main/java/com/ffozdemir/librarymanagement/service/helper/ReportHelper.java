package com.ffozdemir.librarymanagement.service.helper;

import com.ffozdemir.librarymanagement.entity.concretes.business.Book;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import com.ffozdemir.librarymanagement.payload.mappers.BookMapper;
import com.ffozdemir.librarymanagement.payload.mappers.UserMapper;
import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.repository.business.*;
import com.ffozdemir.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReportHelper {

    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final PageableHelper pageableHelper;
    private final BookMapper bookMapper;
    private final UserMapper userMapper;

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

    public Page<UserResponse> getMostBorrowers(int page, int size) {
        Pageable pageable = pageableHelper.getPageable(page, size, "loanCount", "desc");
        Page<User> users = userRepository.findAllMostBorrowers(pageable);
        return users.map(userMapper::mapUserToUserResponse);
    }
}
