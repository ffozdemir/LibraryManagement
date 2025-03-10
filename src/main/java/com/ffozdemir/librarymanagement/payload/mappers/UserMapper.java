package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.user.Role;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import com.ffozdemir.librarymanagement.exception.BadRequestException;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.user.CreateUserRequest;
import com.ffozdemir.librarymanagement.payload.request.user.RegisterOrUpdateRequest;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public User mapRegisterRequestToUser(RegisterOrUpdateRequest registerOrUpdateRequest) {
        return User.builder()
                .firstName(registerOrUpdateRequest.getFirstName())
                .lastName(registerOrUpdateRequest.getLastName())
                .address(registerOrUpdateRequest.getAddress())
                .phone(registerOrUpdateRequest.getPhone())
                .birthDate(registerOrUpdateRequest.getBirthDate())
                .email(registerOrUpdateRequest.getEmail())
                .password(passwordEncoder.encode(registerOrUpdateRequest.getPassword()))
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


    public User mapRegisterOrUpdateRequestToUser(RegisterOrUpdateRequest registerOrUpdateRequest, User updatedUser) {
        User.UserBuilder user = User.builder()
                .id(updatedUser.getId())
                .firstName(registerOrUpdateRequest.getFirstName())
                .lastName(registerOrUpdateRequest.getLastName())
                .address(registerOrUpdateRequest.getAddress())
                .phone(registerOrUpdateRequest.getPhone())
                .birthDate(registerOrUpdateRequest.getBirthDate())
                .email(registerOrUpdateRequest.getEmail())
                .role(updatedUser.getRole())
                .builtIn(updatedUser.isBuiltIn())
                .createDate(updatedUser.getCreateDate());

        if (StringUtils.hasText(registerOrUpdateRequest.getPassword())) {
            if (passwordEncoder.matches(registerOrUpdateRequest.getPassword(), updatedUser.getPassword())) {
                throw new BadRequestException(ErrorMessages.PASSWORD_SHOULD_NOT_MATCHED);
            }
            user.password(passwordEncoder.encode(registerOrUpdateRequest.getPassword()));
        } else {
            user.password(updatedUser.getPassword());
        }

        return user.build();
    }


}
