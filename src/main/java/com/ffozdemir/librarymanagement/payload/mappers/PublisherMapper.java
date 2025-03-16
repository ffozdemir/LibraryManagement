package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.business.Publisher;
import com.ffozdemir.librarymanagement.payload.request.business.PublisherRequest;
import com.ffozdemir.librarymanagement.payload.response.business.PublisherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublisherMapper {
    public PublisherResponse publisherToPublisherResponse(Publisher publisher) {
        return PublisherResponse.builder()
                .name(publisher.getName())
                .build();
    }

    public Publisher publisherRequestToPublisher(PublisherRequest publisherRequest) {
        return Publisher.builder()
                .name(publisherRequest.getName())
                .build();
    }
}
