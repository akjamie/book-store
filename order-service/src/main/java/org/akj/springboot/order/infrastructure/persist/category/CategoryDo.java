package org.akj.springboot.order.infrastructure.persist.category;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.akj.springboot.common.domain.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
@Data
public class CategoryDo extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	private String description;

	private Long parentId;
}
