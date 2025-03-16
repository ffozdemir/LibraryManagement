package com.ffozdemir.librarymanagement.repository.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameEqualsIgnoreCase(String name);

    boolean existsByNameEqualsIgnoreCaseAndIdNot(String name, Long id);
}
