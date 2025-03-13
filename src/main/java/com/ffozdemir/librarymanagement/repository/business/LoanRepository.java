package com.ffozdemir.librarymanagement.repository.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByUser_Id(Long id);


    Optional<Loan> findLoanByIdAndUser_Id(Long id, Long userId);

    Page<Loan> findAllByUser_Id(Long userId, Pageable pageable);

    Page<Loan> findAllByBook_Id(Long bookId, Pageable pageable);

    boolean existsByBook_Id(Long bookId);

    boolean existsByUser_IdAndReturnDateIsNullAndExpireDateBefore(Long userId, LocalDateTime expireDateBefore);

    List<Loan> findAllByUser_IdAndReturnDateIsNull(Long userId);

    Long countByReturnDateIsNull();

    Long countByExpireDateBefore(LocalDateTime expireDateBefore);
}
