package com.ffozdemir.librarymanagement.service.validator;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.exception.ConflictException;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.user.RegisterOrUpdateRequest;
import com.ffozdemir.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkDuplication(String email, String phone) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.existsByPhone(phone)) {
            throw new ConflictException(ErrorMessages.PHONE_ALREADY_EXISTS);
        }
    }

    public void checkUniqueProperty(User user, RegisterOrUpdateRequest registerOrUpdateRequest) {
        String updatedEmail = registerOrUpdateRequest.getEmail();
        String updatedPhone = registerOrUpdateRequest.getPhone();

        if (updatedEmail != null && !user.getEmail().equals(updatedEmail) && userRepository.existsByEmail(updatedEmail)) {
            throw new ConflictException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        if (updatedPhone != null && !user.getPhone().equals(updatedPhone) && userRepository.existsByPhone(updatedPhone)) {
            throw new ConflictException(ErrorMessages.PHONE_ALREADY_EXISTS);
        }
    }
}
