package com.ffozdemir.librarymanagement.service.user;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.payload.mappers.UserMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.authentication.LoginRequest;
import com.ffozdemir.librarymanagement.payload.request.user.RegisterRequest;
import com.ffozdemir.librarymanagement.payload.response.authentication.AuthenticationResponse;
import com.ffozdemir.librarymanagement.payload.response.user.UserResponse;
import com.ffozdemir.librarymanagement.security.jwt.JwtUtils;
import com.ffozdemir.librarymanagement.security.service.UserDetailsImpl;
import com.ffozdemir.librarymanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;

    public AuthenticationResponse authenticate(
            LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        char[] password = loginRequest.getPassword()
                .toCharArray();

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, new String(password)));

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            String token = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String userRole = userDetails.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();

            return AuthenticationResponse.builder()
                    .token(token)
                    .role(userRole)
                    .email(email)
                    .build();
        } catch (AuthenticationException e) {
            throw new AuthenticationException(ErrorMessages.INVALID_CREDENTIALS) {
            };
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    public UserResponse register(
            RegisterRequest registerRequest) {
        uniquePropertyValidator.checkDuplication(registerRequest.getEmail(), registerRequest.getPhone());
        User userToSave = userMapper.mapRegisterRequestToUser(registerRequest);
        return userMapper.mapUserToUserResponse(userService.saveUser(userToSave));
    }
}
