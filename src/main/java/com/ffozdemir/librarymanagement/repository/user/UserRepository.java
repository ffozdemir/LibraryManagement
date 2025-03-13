package com.ffozdemir.librarymanagement.repository.user;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
