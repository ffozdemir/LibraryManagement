package com.ffozdemir.librarymanagement.controller.business;

import com.ffozdemir.librarymanagement.payload.request.business.AuthorRequest;
import com.ffozdemir.librarymanagement.payload.response.business.AuthorResponse;
import com.ffozdemir.librarymanagement.service.business.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/authors")
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest authorRequest) {
        return new ResponseEntity<>(authorService.createAuthor(authorRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Staff', 'Member')")
    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorResponse(id));
    }

    @PreAuthorize("hasAnyAuthority('Admin','Staff', 'Member')")
    @GetMapping("/authors")
    public ResponseEntity<Page<AuthorResponse>> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(authorService.getAllAuthors(page, size, sort, direction));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable Long id,
                                                             @Valid @RequestBody AuthorRequest authorRequest) {
        return ResponseEntity.ok(authorService.updateAuthor(id, authorRequest));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<AuthorResponse> deleteAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.deleteAuthor(id));
    }

}
