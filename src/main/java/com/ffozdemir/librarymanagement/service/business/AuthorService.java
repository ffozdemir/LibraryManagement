package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.repository.business.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

}
