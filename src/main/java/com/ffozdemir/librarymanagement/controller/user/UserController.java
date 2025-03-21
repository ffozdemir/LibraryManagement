package com.ffozdemir.librarymanagement.controller.user;

import com.ffozdemir.librarymanagement.payload.request.user.CreateUserRequest;
import com.ffozdemir.librarymanagement.payload.request.user.RegisterOrUpdateRequest;
import com.ffozdemir.librarymanagement.payload.response.business.LoanResponseForMember;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff', 'Member')")
    @GetMapping("/user")
    public ResponseEntity<UserResponse> getLoggedInUserInfo(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.getLoggedInUserInfo(httpServletRequest));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff', 'Member')")
    @GetMapping("/user/loans")
    public ResponseEntity<Page<LoanResponseForMember>> getAllUserLoans(HttpServletRequest httpServletRequest,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "20") int size,
                                                                       @RequestParam(defaultValue = "createDate") String sort,
                                                                       @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(userService.getAllUserLoans(httpServletRequest, page, size, sort, direction));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/users")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortBy, sortDirection));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(HttpServletRequest httpServletRequest,
                                                   @Valid @RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(userService.createUser(httpServletRequest, createUserRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUserById(HttpServletRequest httpServletRequest, @PathVariable Long id,
                                                       @Valid @RequestBody RegisterOrUpdateRequest registerOrUpdateRequest) {
        return ResponseEntity.ok(userService.updateUserById(httpServletRequest, id, registerOrUpdateRequest));
    }
}
