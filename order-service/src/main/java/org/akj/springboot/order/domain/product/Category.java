package org.akj.springboot.order.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.akj.springboot.order.domain.Base;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Category extends Base {
	private Long id;

	private String name;

	private String description;

	private Category parent;

	public static Category fromId(Long id) {
		return new Category(id, null, null, null);
	}
}
