package com.ffozdemir.librarymanagement.service.helper;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {
	private final UserRepository userRepository;

	public User loadUserByEmail(
				String email) {
		return userRepository.findByEmail(email)
					       .orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, email)));
	}
}
