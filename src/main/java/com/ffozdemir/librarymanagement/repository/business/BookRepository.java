package com.ffozdemir.librarymanagement.repository.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    Page<Book> findAllByCategoryIdAndNameContainingIgnoreCase(Long categoryId, String name, Pageable pageable);

    Page<Book> findAllByCategoryIdAndNameContainingIgnoreCaseAndActiveTrue(Long categoryId, String name, Pageable pageable);

    Page<Book> findAllByAuthorIdAndNameContainingIgnoreCase(Long authorId, String name, Pageable pageable);

    Page<Book> findAllByAuthorIdAndNameContainingIgnoreCaseAndActiveTrue(Long authorId, String name, Pageable pageable);

    Page<Book> findAllByPublisherIdAndNameContainingIgnoreCase(Long publisherId, String name, Pageable pageable);

    Page<Book> findAllByPublisherIdAndNameContainingIgnoreCaseAndActiveTrue(Long publisherId, String name, Pageable pageable);

    Page<Book> findAllByNameContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrIsbnContainingIgnoreCaseOrPublisherNameContainingIgnoreCase(String name, String authorName, String isbn, String publisherName, Pageable pageable);

    Page<Book> findAllByNameContainingOrAuthorNameContainingOrIsbnContainingOrPublisherNameContainingIgnoreCaseAndActiveTrue(String name, String authorName, String isbn, String publisherName, boolean active, Pageable pageable);

    @Query(value = "SELECT b.* FROM book b " +
            "JOIN (SELECT book_id, COUNT(book_id) as loan_count FROM loan GROUP BY book_id " +
            "ORDER BY COUNT(book_id) DESC LIMIT :amount) l " +
            "ON b.id = l.book_id " +
            "ORDER BY l.loan_count DESC",
            countQuery = "SELECT COUNT(*) FROM (SELECT book_id FROM loan GROUP BY book_id " +
                    "ORDER BY COUNT(book_id) DESC LIMIT :amount) AS count_query",
            nativeQuery = true)
    Page<Book> findMostBorrowedBooks(@Param("amount") int amount, Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b JOIN Loan l ON b.id = l.book.id WHERE l.returnDate IS NULL")
    Page<Book> findAllByLoans_ReturnDateIsNull(Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b JOIN Loan l ON b.id = l.book.id WHERE l.expireDate < :now and l.returnDate is null")
    Page<Book> findAllByExpireDateBeforeAndNotReturned(LocalDateTime now, Pageable pageable);


}
