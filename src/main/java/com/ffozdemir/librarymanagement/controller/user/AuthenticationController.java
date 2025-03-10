package com.ffozdemir.librarymanagement.controller.user;

import com.ffozdemir.librarymanagement.payload.request.authentication.LoginRequest;
import com.ffozdemir.librarymanagement.payload.request.user.RegisterOrUpdateRequest;
import com.ffozdemir.librarymanagement.payload.response.authentication.AuthenticationResponse;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.service.user.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody @Valid RegisterOrUpdateRequest registerOrUpdateRequest) {
        return new ResponseEntity<>(authenticationService.register(registerOrUpdateRequest), HttpStatus.CREATED);
    }

}
