package com.ffozdemir.librarymanagement.service.helper;

import com.ffozdemir.librarymanagement.entity.concretes.business.Author;
import com.ffozdemir.librarymanagement.entity.concretes.business.Category;
import com.ffozdemir.librarymanagement.entity.concretes.business.Loan;
import com.ffozdemir.librarymanagement.entity.concretes.business.Publisher;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.repository.business.AuthorRepository;
import com.ffozdemir.librarymanagement.repository.business.CategoryRepository;
import com.ffozdemir.librarymanagement.repository.business.LoanRepository;
import com.ffozdemir.librarymanagement.repository.business.PublisherRepository;
import com.ffozdemir.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {
	private final UserRepository userRepository;
	private final AuthorRepository authorRepository;
	private final PublisherRepository publisherRepository;
	private final CategoryRepository categoryRepository;
	private final LoanRepository loanRepository;

	public User loadUserByEmail(
				String email) {
		return userRepository.findByEmail(email)
					       .orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_BY_EMAIL, email)));
	}

	public User isUserExistById(Long id) {
		return userRepository.findById(id)
					       .orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_BY_ID, id)));
	}

	public Author getAuthorById(Long authorId) {
		return authorRepository.findById(authorId)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(ErrorMessages.AUTHOR_NOT_FOUND_BY_ID, authorId)));
	}

	public Publisher getPublisherById(Long publisherId) {
		return publisherRepository.findById(publisherId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PUBLISHER_NOT_FOUND_BY_ID,
						publisherId)));
	}

	public Category getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND_BY_ID, categoryId)));
	}

	public Loan getLoanById(Long loanId) {
		return loanRepository.findById(loanId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.LOAN_NOT_FOUND_BY_ID, loanId)));
	}

	public boolean isBookRelatedToLoan(Long bookId) {
		return loanRepository.existsByBook_Id((bookId));
	}


}
