package com.ffozdemir.librarymanagement.service.user;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import com.ffozdemir.librarymanagement.payload.mappers.UserMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.user.CreateUserRequest;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.repository.user.UserRepository;
import com.ffozdemir.librarymanagement.service.business.LoanService;
import com.ffozdemir.librarymanagement.service.helper.MethodHelper;
import com.ffozdemir.librarymanagement.service.helper.PageableHelper;
import com.ffozdemir.librarymanagement.service.validator.UniquePropertyValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;
    private final LoanService loanService;


    public User saveUser(
            User user) {
        uniquePropertyValidator.checkDuplication(user.getEmail(), user.getPhone());
        return userRepository.save(user);
    }

    public UserResponse getLoggedInUserInfo(HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.loadUserByEmail(email);
        return userMapper.mapUserToUserResponse(user);
    }

    public Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = pageableHelper.getPageable(page, size, sortBy, sortDirection);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(userMapper::mapUserToUserResponse);
    }

    public UserResponse getUserById(Long id) {
        User user = methodHelper.isUserExistById(id);
        return userMapper.mapUserToUserResponse(user);
    }

    public UserResponse createUser(HttpServletRequest httpServletRequest, CreateUserRequest createUserRequest) {
        String email = (String) httpServletRequest.getAttribute("email");
        User user = methodHelper.loadUserByEmail(email);
        RoleType userRoleType = user.getRole().getRoleType();
        uniquePropertyValidator.checkDuplication(createUserRequest.getEmail(), createUserRequest.getPhone());
        User newUser = userMapper.mapCreateUserRequestToUser(createUserRequest);

        if (userRoleType == RoleType.STAFF && !newUser.getRole().getRoleType().equals(RoleType.MEMBER)) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED_MESSAGE);
        }
        return userMapper.mapUserToUserResponse(userRepository.save(newUser));
    }

    public UserResponse deleteUserById(Long id) {
        User user = methodHelper.isUserExistById(id);
        if (user.isBuiltIn()){
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED_MESSAGE);
        }
        //TODO check this method when loan service is completed
        if (loanService.isUserHasLoan(id)) {
            throw new AccessDeniedException(ErrorMessages.USER_HAS_LOAN_MESSAGE);
        }
        userRepository.delete(user);
        return userMapper.mapUserToUserResponse(user);
    }
}

