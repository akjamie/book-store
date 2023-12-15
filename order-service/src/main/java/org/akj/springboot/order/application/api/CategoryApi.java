package org.akj.springboot.order.application.api;

import jakarta.validation.constraints.NotNull;
import org.akj.springboot.common.domain.Result;
import org.akj.springboot.order.application.api.dto.CategoryDto;
import org.akj.springboot.order.application.api.mapper.CategoryDtoMapstructMapper;
import org.akj.springboot.order.domain.product.Category;
import org.akj.springboot.order.domain.product.service.CategoryDomainService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/categories")
public class CategoryApi {

	private final CategoryDtoMapstructMapper categoryDtoMapstructMapper;

	private final CategoryDomainService categoryDomainService;

	public CategoryApi(CategoryDtoMapstructMapper categoryDtoMapstructMapper, CategoryDomainService categoryDomainService) {
		this.categoryDtoMapstructMapper = categoryDtoMapstructMapper;
		this.categoryDomainService = categoryDomainService;
	}

	@PutMapping
	@PreAuthorize("hasRole('CATEGORY-ADMIN') or hasRole('CATEGORY-EDITOR')")
	public Result createCategory(@Validated @RequestBody CategoryDto categoryDto) {
		Category category = categoryDomainService.save(categoryDtoMapstructMapper.convert(categoryDto));

		CategoryDto dto = categoryDtoMapstructMapper.convert(category);
		return Result.success(dto);
	}

	@GetMapping("/{id}")
	public Result getCategoryById(@PathVariable("id") @NotNull Long id) {
		return Result.success(categoryDtoMapstructMapper.convert(categoryDomainService.findById(id)));
	}

	@PostMapping("/{id}")
	@PreAuthorize("hasRole('CATEGORY-ADMIN') or hasRole('CATEGORY-EDITOR')")
	public Result updateCategory(@PathVariable("id") @NotNull Long id, @Validated @RequestBody CategoryDto categoryDto) {
		return Result.success(categoryDtoMapstructMapper.convert(categoryDomainService.update(id, categoryDtoMapstructMapper.convert(categoryDto))));
	}

	@PreAuthorize("hasRole('CATEGORY-ADMIN')")
	@DeleteMapping("/{id}")
	public Result deleteCategory(@PathVariable("id") @NotNull Long id) {
		categoryDomainService.deleteById(id);
		return Result.successWithEmptyResponse();
	}

}
