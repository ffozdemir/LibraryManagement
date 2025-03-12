package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Author;
import com.ffozdemir.librarymanagement.exception.ConflictException;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.mappers.AuthorMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.business.AuthorRequest;
import com.ffozdemir.librarymanagement.payload.response.business.AuthorResponse;
import com.ffozdemir.librarymanagement.repository.business.AuthorRepository;
import com.ffozdemir.librarymanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final PageableHelper pageableHelper;

    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.AUTHOR_NOT_FOUND_BY_ID, authorId)));
    }

    public AuthorResponse getAuthorResponse(Long id) {
        return authorMapper.authorToAuthorResponse(getAuthorById(id));
    }

    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        if (authorRepository.existsByNameEqualsIgnoreCase((authorRequest.getName()))) {
            throw new ConflictException(String.format(ErrorMessages.AUTHOR_ALREADY_EXISTS_WITH_NAME,
                    authorRequest.getName()));
        }
        Author author = authorMapper.authorRequestToAuthor(authorRequest);
        return authorMapper.authorToAuthorResponse(authorRepository.save(author));
    }

    public AuthorResponse deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        if (!author.getBooks().isEmpty()) {
            throw new ConflictException(String.format(ErrorMessages.AUTHOR_NOT_DELETABLE_WITH_BOOKS,
                    author.getName()));
        }
        authorRepository.delete(author);
        return authorMapper.authorToAuthorResponse(author);
    }

    public AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest) {
        Author author = getAuthorById(id);
        if (authorRepository.existsByNameEqualsIgnoreCaseAndIdNot(authorRequest.getName(), id)) {
            throw new ConflictException(String.format(ErrorMessages.AUTHOR_ALREADY_EXISTS_WITH_NAME,
                    authorRequest.getName()));
        }
        author.setName(authorRequest.getName());
        return authorMapper.authorToAuthorResponse(authorRepository.save(author));
    }

    public Page<AuthorResponse> getAllAuthors(int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Author> authors = authorRepository.findAll(pageable);
        return authors.map(authorMapper::authorToAuthorResponse);
    }


}
