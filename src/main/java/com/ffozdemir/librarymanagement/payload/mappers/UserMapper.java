package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.user.Role;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import com.ffozdemir.librarymanagement.payload.request.abstracts.BaseUserRequest;
import com.ffozdemir.librarymanagement.payload.request.user.CreateUserRequest;
import com.ffozdemir.librarymanagement.payload.request.user.RegisterRequest;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public User mapRegisterRequestToUser(RegisterRequest registerRequest) {
        return User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .address(registerRequest.getAddress())
                .phone(registerRequest.getPhone())
                .birthDate(registerRequest.getBirthDate())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(roleService.getRoleByName(RoleType.MEMBER))
                .build();
    }

    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .build();
    }

    public User mapCreateUserRequestToUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .address(createUserRequest.getAddress())
                .phone(createUserRequest.getPhone())
                .birthDate(createUserRequest.getBirthDate())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .role(convertRoleType(createUserRequest.getRoleType()))
                .build();
    }

    private Role convertRoleType(String roleType) {
        try {
            return roleService.getRoleByName(RoleType.valueOf(roleType.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Role Type " + roleType);
        }
    }
}
