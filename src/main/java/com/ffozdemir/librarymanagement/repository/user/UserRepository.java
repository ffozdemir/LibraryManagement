package com.ffozdemir.librarymanagement.repository.user;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
