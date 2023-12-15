package org.akj.springboot.order.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.akj.springboot.order.domain.Base;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Base {
	private Long id;

	private Category category;

	private String name;

	private String description;

	private String image;

	private String brand;

	private Available available;

}
