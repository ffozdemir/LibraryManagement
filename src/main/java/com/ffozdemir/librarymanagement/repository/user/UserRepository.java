package com.ffozdemir.librarymanagement.repository.user;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(
				String email);

	Optional<User> findByEmail(
				String email);

    boolean existsByPhone(String phone);

    Long countByRole_RoleType(RoleType roleRoleType);

	@Query(value = "SELECT u FROM User u JOIN Loan l on u.id = l.user.id GROUP BY u.id ORDER BY COUNT(l) DESC")
    Page<User> findAllMostBorrowers(Pageable pageable);
}
