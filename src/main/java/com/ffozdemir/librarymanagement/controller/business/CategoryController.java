package com.ffozdemir.librarymanagement.controller.business;

import com.ffozdemir.librarymanagement.payload.request.business.CategoryRequest;
import com.ffozdemir.librarymanagement.payload.response.business.CategoryResponse;
import com.ffozdemir.librarymanagement.service.business.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.createCategory(categoryRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff', 'Member')")
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Staff', 'Member')")
    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(categoryService.getAllCategories(page, size, sort, direction));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequest));
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
