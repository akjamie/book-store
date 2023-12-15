package org.akj.springboot.order.application.api.mapper;

import org.akj.springboot.order.application.api.dto.CategoryDto;
import org.akj.springboot.order.domain.product.Category;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS)
public interface CategoryDtoMapstructMapper {
	Category convert(CategoryDto dto);

	CategoryDto convert(Category category);
}
