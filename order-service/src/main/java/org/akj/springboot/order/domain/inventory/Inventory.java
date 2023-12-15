package org.akj.springboot.order.domain.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.akj.springboot.order.domain.Base;
import org.akj.springboot.order.domain.product.Product;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends Base {
	private Long id;

	private Product product;

	private Long quantity;
}