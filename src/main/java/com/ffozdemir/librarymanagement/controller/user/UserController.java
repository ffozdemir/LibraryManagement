package com.ffozdemir.librarymanagement.controller.user;

import com.ffozdemir.librarymanagement.payload.request.user.CreateUserRequest;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    //TODO: Uncomment and implement the following method when needed
    /*@PreAuthorize("hasAnyAuthority('Admin', 'Staff', 'Member')")
    @GetMapping("/user/loan")*/

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff')")
    @GetMapping("/users")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
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
                                                   @RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(httpServletRequest, createUserRequest));
    }


}
