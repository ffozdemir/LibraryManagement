package com.ffozdemir.librarymanagement.repository.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    boolean existsByNameEqualsIgnoreCase(String name);

    boolean existsByNameEqualsIgnoreCaseAndIdNot(String name, Long id);
}
