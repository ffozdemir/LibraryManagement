package com.ffozdemir.librarymanagement.controller.business;

import com.ffozdemir.librarymanagement.payload.request.business.PublisherRequest;
import com.ffozdemir.librarymanagement.payload.response.business.PublisherResponse;
import com.ffozdemir.librarymanagement.service.business.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publisher")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/publishers")
    public ResponseEntity<PublisherResponse> createPublisher(@Valid @RequestBody PublisherRequest publisherRequest) {
        return new ResponseEntity<>(publisherService.createPublisher(publisherRequest), HttpStatus.CREATED);
    }

    @GetMapping("/publishers/{id}")
    public ResponseEntity<PublisherResponse> getPublisherById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.getPublisherResponse(id));
    }

    @GetMapping("/publishers")
    public ResponseEntity<Page<PublisherResponse>> getAllPublishers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(publisherService.getAllPublishers(page, size, sort, direction));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/publishers/{id}")
    public ResponseEntity<PublisherResponse> updatePublisher(@PathVariable Long id,
                                                             @Valid @RequestBody PublisherRequest publisherRequest) {
        return ResponseEntity.ok(publisherService.updatePublisher(id, publisherRequest));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/publishers/{id}")
    public ResponseEntity<PublisherResponse> deletePublisher(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.deletePublisher(id));
    }
}

