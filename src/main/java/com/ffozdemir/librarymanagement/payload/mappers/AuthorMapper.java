package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.business.Author;
import com.ffozdemir.librarymanagement.payload.request.business.AuthorRequest;
import com.ffozdemir.librarymanagement.payload.response.business.AuthorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorMapper {

    public AuthorResponse authorToAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .name(author.getName())
                .build();
    }

    public Author authorRequestToAuthor(AuthorRequest authorRequest) {
        return Author.builder()
                .name(authorRequest.getName())
                .build();
    }

}
