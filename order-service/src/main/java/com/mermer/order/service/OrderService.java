package com.mermer.order.service;

import com.mermer.order.dto.OrderDto;
import com.mermer.order.entity.OrderEntity;

public interface OrderService {

	OrderDto createOrder(OrderDto orderDto);
	OrderDto getOrderByOrderId(String orderId);
	Iterable<OrderEntity> getOrderByUserId(String userId);
}
