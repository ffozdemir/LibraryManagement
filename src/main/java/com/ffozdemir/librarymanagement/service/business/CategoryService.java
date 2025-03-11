package com.ffozdemir.librarymanagement.service.business;

import com.ffozdemir.librarymanagement.entity.concretes.business.Category;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.repository.business.CategoryRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category isCategoryExists(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND_BY_ID, categoryId)));
    }


	/*private void setSequence() {
		if (this.sequence == 0) {
			// Varsayılan olarak mevcut en büyük sequence değerinden bir fazla olacak şekilde ayarla
			this.sequence = findMaxSequence() + 1;
		}
	}

	private int findMaxSequence() {
		// En büyük sequence değerini bulmak için gerekli kodu buraya ekleyin
		// Örneğin, bir repository kullanarak veritabanından en büyük sequence değerini alabilirsiniz
		return 0; // Bu satırı uygun kodla değiştirin
	}*/
}
