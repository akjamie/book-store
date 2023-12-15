package org.akj.springboot.order.domain.order;

public enum Status {
	PENDING("Pending", 0),
	PENDING_PAYMENT("Pending payment", 1),
	PAYMENT_RECEIVED("Payment received", 2),
	PAYMENT_FAILED("Payment failed", 1),
	PACKING("Packing", 2),
	READY_FOR_SHIPMENT("Ready for shipment", 2),
	SHIPPED("Shipped", 3),
	IN_TRANSIT("In transit", 3),
	OUT_FOR_DELIVERY("Out for delivery", 4),
	DELIVERED("Delivered", 4),
	CANCELLED("Cancelled", 5),
	REFUNDED("Refunded", 7),
	RETURNED("Returned", 6),
	CLOSED("Closed", 5);

	private final String description;

	private final Integer stage;

	Status(String description, Integer stage){
		this.description = description;
		this.stage = stage;
	}

	public String getDescription() {
		return description;
	}
}
