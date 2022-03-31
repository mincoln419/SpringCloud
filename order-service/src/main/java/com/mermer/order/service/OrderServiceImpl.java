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
	
	private ModelMapper modelMapper;
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository,  ModelMapper modelMapper) {
		this.orderRepository = orderRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		orderDto.setOrderId(UUID.randomUUID().toString());
		orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());
		

		OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
		
		OrderEntity newOrder = orderRepository.save(orderEntity);
		OrderDto returnValue = modelMapper.map(newOrder, OrderDto.class);
		
		return returnValue;
	}

	@Override
	public OrderDto getOrderByOrderId(String orderId) {
		OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
		
		OrderDto returnValue = modelMapper.map(orderEntity, OrderDto.class);
		return returnValue;
	}

	@Override
	public Iterable<OrderEntity> getOrderByUserId(String userId) {
		return orderRepository.findByUserId(userId);
	}

}
