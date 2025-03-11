package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Category;
import com.ffozdemir.librarymanagement.exception.ConflictException;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.mappers.CategoryMapper;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.payload.request.business.CategoryRequest;
import com.ffozdemir.librarymanagement.payload.response.business.CategoryResponse;
import com.ffozdemir.librarymanagement.repository.business.CategoryRepository;
import com.ffozdemir.librarymanagement.service.helper.PageableHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final PageableHelper pageableHelper;

    public Category isCategoryExists(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND_BY_ID, categoryId)));
    }

    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.categoryToCategoryResponse(isCategoryExists(id));
    }


    public Page<CategoryResponse> getAllCategories(int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, direction);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(categoryMapper::categoryToCategoryResponse);
    }

    public CategoryResponse createCategory(@Valid CategoryRequest categoryRequest) {
        if (categoryRepository.existsByNameEqualsIgnoreCase((categoryRequest.getName()))) {
            throw new ConflictException(String.format(ErrorMessages.CATEGORY_ALREADY_EXISTS_WITH_NAME,
                    categoryRequest.getName()));
        }
        Category category = categoryMapper.categoryRequestToCategory(categoryRequest);
        return categoryMapper.categoryToCategoryResponse(categoryRepository.save(category));
    }

    public CategoryResponse deleteCategory(Long id) {
        Category category = isCategoryExists(id);
        if (!category.getBooks().isEmpty()) {
            throw new ConflictException(ErrorMessages.CATEGORY_NOT_DELETABLE_WITH_BOOKS);
        }
        categoryRepository.delete(category);
        return categoryMapper.categoryToCategoryResponse(category);
    }

    public CategoryResponse updateCategory(Long id, @Valid CategoryRequest categoryRequest) {
        Category category = isCategoryExists(id);
        if (categoryRepository.existsByNameEqualsIgnoreCaseAndIdNot(categoryRequest.getName(), id)) {
            throw new ConflictException(String.format(ErrorMessages.CATEGORY_ALREADY_EXISTS_WITH_NAME,
                    categoryRequest.getName()));
        }
        category.setName(categoryRequest.getName());
        return categoryMapper.categoryToCategoryResponse(categoryRepository.save(category));
    }
}
