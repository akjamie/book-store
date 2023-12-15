package org.akj.springboot.order.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.akj.springboot.order.domain.Amount;
import org.akj.springboot.order.domain.Base;

import java.security.Principal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Base {
	private Long id;

	private Principal user;

	private Instant orderedAt;

	private Amount totalAmount;

	private Status status;

	private Address shippingAddress;

	private PaymentMethod paymentMethod;

}
