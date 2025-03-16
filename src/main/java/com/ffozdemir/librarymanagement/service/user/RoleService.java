package com.ffozdemir.librarymanagement.service.user;

import com.ffozdemir.librarymanagement.entity.concretes.user.Role;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(RoleType roleType) {
        return roleRepository.findByRoleType(roleType)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.ROLE_NOT_FOUND, roleType.getName())));
    }

    public void saveRole(Role role) {
        if (!roleRepository.existsByRoleType(role.getRoleType())) {
            roleRepository.save(role);
        }
    }
}
