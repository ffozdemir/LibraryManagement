package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.repository.business.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
}
