package com.ffozdemir.librarymanagement.payload.mappers;

import com.ffozdemir.librarymanagement.entity.concretes.business.Category;
import com.ffozdemir.librarymanagement.payload.request.business.CategoryRequest;
import com.ffozdemir.librarymanagement.payload.response.business.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public Category categoryRequestToCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }

    public CategoryResponse categoryToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .name(category.getName())
                .build();
    }

}
