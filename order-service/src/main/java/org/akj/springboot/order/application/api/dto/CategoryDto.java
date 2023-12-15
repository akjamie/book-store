package org.akj.springboot.order.application.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryDto{
	private Long id;

	@NotNull
	@Length(min = 2, max = 256)
	private String name;

	@NotNull
	@Length(min = 0, max = 1024)
	private String description;

	private Long parentId;
}
