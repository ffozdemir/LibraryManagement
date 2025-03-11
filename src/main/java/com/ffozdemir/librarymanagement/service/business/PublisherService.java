package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Publisher;
import com.ffozdemir.librarymanagement.exception.ConflictException;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.mappers.PublisherMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.business.PublisherRequest;
import com.ffozdemir.librarymanagement.payload.response.business.PublisherResponse;
import com.ffozdemir.librarymanagement.repository.business.PublisherRepository;
import com.ffozdemir.librarymanagement.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;
    private final PageableHelper pageableHelper;

    public Publisher getPublisherById(Long publisherId) {
        return publisherRepository.findById(publisherId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PUBLISHER_NOT_FOUND_BY_ID,
                        publisherId)));
    }

    public PublisherResponse getPublisherResponse(Long id) {
        return publisherMapper.publisherToPublisherResponse(getPublisherById(id));
    }

    public PublisherResponse createPublisher(PublisherRequest publisherRequest) {
        if (publisherRepository.existsByNameEqualsIgnoreCase((publisherRequest.getName()))) {
            throw new ConflictException(String.format(ErrorMessages.PUBLISHER_ALREADY_EXISTS_WITH_NAME,
                    publisherRequest.getName()));
        }
        Publisher publisher = publisherMapper.publisherRequestToPublisher(publisherRequest);
        return publisherMapper.publisherToPublisherResponse(publisherRepository.save(publisher));
    }

    public PublisherResponse deletePublisher(Long id) {
        Publisher publisher = getPublisherById(id);
        if (!publisher.getBooks().isEmpty()) {
            throw new ConflictException(String.format(ErrorMessages.PUBLISHER_NOT_DELETABLE_WITH_BOOKS,
                    publisher.getName()));
        }
        publisherRepository.delete(publisher);
        return publisherMapper.publisherToPublisherResponse(publisher);
    }

    public PublisherResponse updatePublisher(Long id, PublisherRequest publisherRequest) {
        Publisher publisher = getPublisherById(id);
        if (publisherRepository.existsByNameEqualsIgnoreCaseAndIdNot(publisherRequest.getName(), id)) {
            throw new ConflictException(String.format(ErrorMessages.PUBLISHER_ALREADY_EXISTS_WITH_NAME,
                    publisherRequest.getName()));
        }
        publisher.setName(publisherRequest.getName());
        return publisherMapper.publisherToPublisherResponse(publisherRepository.save(publisher));
    }

    public Page<PublisherResponse> getAllPublishers(int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Publisher> publishers = publisherRepository.findAll(pageable);
        return publishers.map(publisherMapper::publisherToPublisherResponse);
    }
}
