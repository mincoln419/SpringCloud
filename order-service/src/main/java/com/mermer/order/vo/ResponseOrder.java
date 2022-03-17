package com.mermer.order.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {

	private String productId;
	private String productName;
	private Integer qty;
	private Integer unitPrice;
	private Integer totalPrice;
	private LocalDateTime createAt;

	private String orderId;
}
