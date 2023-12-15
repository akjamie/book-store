package org.akj.springboot.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.security.Principal;
import java.time.Instant;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Base {
	private Instant createdAt;

	private Instant updatedAt;

	private String createdBy;

	private String updatedBy;
}
