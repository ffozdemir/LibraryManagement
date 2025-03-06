package com.ffozdemir.librarymanagement.repository.user;

import com.ffozdemir.librarymanagement.entity.concretes.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
