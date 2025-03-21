package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Author;
import com.ffozdemir.librarymanagement.entity.concretes.business.Book;
import com.ffozdemir.librarymanagement.entity.concretes.business.Category;
import com.ffozdemir.librarymanagement.entity.concretes.business.Publisher;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import com.ffozdemir.librarymanagement.exception.BadRequestException;
import com.ffozdemir.librarymanagement.exception.ConflictException;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.mappers.BookMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.business.BookRequest;
import com.ffozdemir.librarymanagement.payload.response.business.BookResponse;
import com.ffozdemir.librarymanagement.repository.business.BookRepository;
import com.ffozdemir.librarymanagement.service.helper.MethodHelper;
import com.ffozdemir.librarymanagement.service.helper.PageableHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;

    public BookResponse createBook(BookRequest bookRequest) {
        validateBookIsbn(bookRequest.getIsbn());
        Author author = methodHelper.getAuthorById(bookRequest.getAuthorId());
        Publisher publisher = methodHelper.getPublisherById(bookRequest.getPublisherId());
        Category category = methodHelper.getCategoryById(bookRequest.getCategoryId());
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

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND_BY_ID,
                        id)));
    }

    public BookResponse getBookResponseById(Long id) {
        return bookMapper.bookToBookResponse(getBookById(id));
    }

    public BookResponse updateBook(Long id, @Valid BookRequest bookRequest) {
        Book book = getBookById(id);
        validateBookIsbn(bookRequest.getIsbn());
        Book bookToUpdate = bookMapper.updateBookFromRequest(bookRequest, book);
        return bookMapper.bookToBookResponse(bookRepository.save(bookToUpdate));
    }

    /**
     * Retrieves books based on various filter parameters with pagination support.
     *
     * @param httpServletRequest HTTP request containing user information in headers
     * @param q                  Search query to filter books by name, author name, ISBN, or publisher name
     * @param categoryId         Filter books by specific category ID
     * @param authorId           Filter books by specific author ID
     * @param publisherId        Filter books by specific publisher ID
     * @param page               Page number for pagination (zero-based)
     * @param size               Number of items per page
     * @param sortBy             Field to sort results by
     * @param type               Sort direction ("asc" or "desc")
     * @return Page of BookResponse objects containing filtered book data
     * @throws BadRequestException if no search parameters are provided
     */
    public Page<BookResponse> getAllBooks(HttpServletRequest httpServletRequest, String q, Long categoryId,
                                          Long authorId,
                                          Long publisherId, int page,
                                          int size, String sortBy, String type) {
        if (q == null && categoryId == null && authorId == null && publisherId == null) {
            throw new BadRequestException(ErrorMessages.BOOK_SEARCH_PARAMETERS_NOT_PROVIDED);
        }
        Pageable pageable = pageableHelper.getPageable(page, size, sortBy, type);
        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.loadUserByEmail(email);
        boolean isAdmin = user.getRole().getRoleType().equals(RoleType.ADMIN);

        Page<Book> bookPage = getBooksBasedOnParameters(q, categoryId, authorId, publisherId, pageable, isAdmin);
        return bookPage.map(bookMapper::bookToBookResponse);
    }

    /**
     * Helper method that selects the appropriate repository method based on provided parameters.
     * For admin users, returns all matching books. For non-admin users, returns only active books.
     *
     * @param q           Search query text
     * @param categoryId  Category identifier
     * @param authorId    Author identifier
     * @param publisherId Publisher identifier
     * @param pageable    Pagination information
     * @param isAdmin     Flag indicating if the current user has admin privileges
     * @return Page of Book entities matching the specified criteria
     */
    private Page<Book> getBooksBasedOnParameters(String q, Long categoryId, Long authorId, Long publisherId, Pageable pageable, boolean isAdmin) {
        if (categoryId != null) {
            return isAdmin
                    ? bookRepository.findAllByCategoryIdAndNameContainingIgnoreCase(categoryId, q, pageable)
                    : bookRepository.findAllByCategoryIdAndNameContainingIgnoreCaseAndActiveTrue(categoryId, q,
                    pageable);
        } else if (authorId != null) {
            return isAdmin
                    ? bookRepository.findAllByAuthorIdAndNameContainingIgnoreCase(authorId, q, pageable)
                    : bookRepository.findAllByAuthorIdAndNameContainingIgnoreCaseAndActiveTrue(authorId, q, pageable);
        } else if (publisherId != null) {
            return isAdmin
                    ? bookRepository.findAllByPublisherIdAndNameContainingIgnoreCase(publisherId, q, pageable)
                    : bookRepository.findAllByPublisherIdAndNameContainingIgnoreCaseAndActiveTrue(publisherId, q,
                    pageable);
        } else {
            return isAdmin
                    ? bookRepository.findAllByNameContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrIsbnContainingIgnoreCaseOrPublisherNameContainingIgnoreCase(q, q, q, q, pageable)
                    : bookRepository.findAllByNameContainingOrAuthorNameContainingOrIsbnContainingOrPublisherNameContainingIgnoreCaseAndActiveTrue(q, q, q, q, true, pageable);
        }
    }

    public BookResponse deleteBook(Long id) {
        Book book = getBookById(id);
        if (book.getLoans() != null && !book.getLoans().isEmpty()) {
            throw new BadRequestException(ErrorMessages.BOOK_CANNOT_BE_DELETED);
        }
        bookRepository.delete(book);
        return bookMapper.bookToBookResponse(book);
    }
}
