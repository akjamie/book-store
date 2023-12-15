package org.akj.springboot.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Amount {
	public static final String DEFAULT_CURRENCY = "CNY";
	private String currency;

	private BigDecimal amount;

	public static Amount of(String currency, BigDecimal amount) {
		return new Amount(currency, amount);
	}

	public static Amount of(BigDecimal amount) {
		return new Amount(DEFAULT_CURRENCY, amount);
	}
}