package com.mermer.order.service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mermer.order.dto.OrderDto;
import com.mermer.order.entity.OrderEntity;
import com.mermer.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

	private OrderRepository orderRepository;
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		orderDto.setOrderId(UUID.randomUUID().toString());
		orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);
		
		OrderEntity newOrder = orderRepository.save(orderEntity);
		OrderDto returnValue = mapper.map(newOrder, OrderDto.class);
		
		return returnValue;
	}

	@Override
	public OrderDto getOrderByOrderId(String orderId) {
		OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		OrderDto returnValue = mapper.map(orderEntity, OrderDto.class);
		return returnValue;
	}

	@Override
	public Iterable<OrderEntity> getOrderByUserId(String userId) {
		return orderRepository.findByUserId(userId);
	}

}
