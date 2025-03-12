package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Publisher;
import com.ffozdemir.librarymanagement.exception.ConflictException;
import com.ffozdemir.librarymanagement.payload.mappers.PublisherMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.business.PublisherRequest;
import com.ffozdemir.librarymanagement.payload.response.business.PublisherResponse;
import com.ffozdemir.librarymanagement.repository.business.PublisherRepository;
import com.ffozdemir.librarymanagement.service.helper.MethodHelper;
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
    private final MethodHelper methodHelper;

    public PublisherResponse getPublisherResponse(Long id) {
        return publisherMapper.publisherToPublisherResponse(methodHelper.getPublisherById(id));
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
        Publisher publisher = methodHelper.getPublisherById(id);
        if (!publisher.getBooks().isEmpty()) {
            throw new ConflictException(ErrorMessages.PUBLISHER_NOT_DELETABLE_WITH_BOOKS);
        }
        publisherRepository.delete(publisher);
        return publisherMapper.publisherToPublisherResponse(publisher);
    }

    public PublisherResponse updatePublisher(Long id, PublisherRequest publisherRequest) {
        Publisher publisher = methodHelper.getPublisherById(id);
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
