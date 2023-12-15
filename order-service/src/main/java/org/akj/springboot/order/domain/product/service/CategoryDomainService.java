package org.akj.springboot.order.domain.product.service;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.order.domain.product.Category;
import org.akj.springboot.order.infrastructure.persist.category.CategoryDo;
import org.akj.springboot.order.infrastructure.persist.category.CategoryMapstructMapper;
import org.akj.springboot.order.infrastructure.persist.category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CategoryDomainService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapstructMapper categoryMapstructMapper;

	public CategoryDomainService(CategoryRepository categoryRepository,
								 CategoryMapstructMapper categoryMapstructMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapstructMapper = categoryMapstructMapper;
	}

	public Category save(Category category) {
		// create id
		CategoryDo categoryDo = categoryRepository.save(categoryMapstructMapper.convert(category));
		return categoryMapstructMapper.convert(categoryDo);
	}

	public Category findById(Long id) {
		Optional<CategoryDo> categoryDo = categoryRepository.findById(id);
		return categoryDo.map(categoryMapstructMapper::convert).orElse(null);
	}

	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}

	public Category update(Long id, Category category) {
		Category savedCategory = findById(id);
		if (Objects.nonNull(savedCategory)) {
			categoryMapstructMapper.merge(category, savedCategory);
			return save(savedCategory);
		}

		return save(category);
	}
}
