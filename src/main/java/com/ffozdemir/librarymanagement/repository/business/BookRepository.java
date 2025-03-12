package com.ffozdemir.librarymanagement.repository.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);


    Page<Book> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<Book> findAllByCategoryIdAndActiveTrue(Long categoryId, boolean b, Pageable pageable);

    Page<Book> findAllByAuthorId(Long authorId, Pageable pageable);

    Page<Book> findAllByAuthorIdAndActiveTrue(Long authorId, boolean b, Pageable pageable);

    Page<Book> findAllByPublisherId(Long publisherId, Pageable pageable);

    Page<Book> findAllByPublisherIdAndActiveTrue(Long publisherId, boolean b, Pageable pageable);


    Page<Book> findAllByNameContainingOrAuthorNameContainingOrIsbnContainingOrPublisherNameContainingIgnoreCase(String name, String authorName, String isbn, String publisherName, Pageable pageable);

    Page<Book> findAllByNameContainingOrAuthorNameContainingOrIsbnContainingOrPublisherNameContainingIgnoreCaseAndActiveTrue(String name, String authorName, String isbn, String publisherName, boolean active, Pageable pageable);
}
