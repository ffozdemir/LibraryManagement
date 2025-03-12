package com.ffozdemir.librarymanagement.repository.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByNameEqualsIgnoreCase(String name);

    boolean existsByNameEqualsIgnoreCaseAndIdNot(String name, Long id);
}
