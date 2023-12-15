package org.akj.springboot.order.infrastructure.persist.category;

import org.akj.springboot.order.domain.product.Category;
import org.akj.springboot.order.infrastructure.persist.common.BaseMapstructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Objects;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS, uses = BaseMapstructMapper.class)
public interface CategoryMapstructMapper extends BaseMapstructMapper {
	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "description", target = "description")
	@Mapping(source = "parent.id", target = "parentId")
	CategoryDo convert(Category category);

	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "description", target = "description")
	@Mapping(target = "parent", expression = "java(customParentIdToCategory(category.getParentId()))")
	Category convert(CategoryDo category);


	@Named("CustomParentIdToCategory")
	default Category customParentIdToCategory(Long parentId) {
		return Objects.nonNull(parentId) ? Category.fromId(parentId) : null;
	}

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	void merge(Category source, @MappingTarget Category target);
}
