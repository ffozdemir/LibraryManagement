package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.repository.business.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;
}
