package com.mermer.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.order.dto.OrderDto;
import com.mermer.order.entity.OrderEntity;
import com.mermer.order.messagequeue.KafkaProducer;
import com.mermer.order.service.OrderService;
import com.mermer.order.vo.RequestOrder;
import com.mermer.order.vo.ResponseOrder;

@RestController
@RequestMapping("/order-service")
public class OrderController {

	private Environment env;
	
	private OrderService orderService;
	
	private ModelMapper modelMapper;
	
	private KafkaProducer kafkaProducer;
	
	@Autowired
	public OrderController(Environment env, OrderService orderService, ModelMapper modelMapper, KafkaProducer kafkaProducer ) {
		this.env = env;
		this.orderService = orderService;
		this.modelMapper = modelMapper;
		this.kafkaProducer = kafkaProducer;
	}

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's working in Order Service on Port %s", env.getProperty("local.server.port"));
	}
	
	
	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(
			@PathVariable("userId") String userId,
			@RequestBody RequestOrder requestOrder){
		
		/* jpa */
		OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
		orderDto.setUserId(userId);
		OrderDto createOrderDto = orderService.createOrder(orderDto);
		
		ResponseOrder responseOrder = modelMapper.map(createOrderDto, ResponseOrder.class);
		
		/* send this order to the kafka*/
		kafkaProducer.send("example-catalog-topic", orderDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}
	
	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId){

		Iterable<OrderEntity> orderList = orderService.getOrderByUserId(userId);
		
		List<ResponseOrder> result = new ArrayList<>();
		orderList.forEach(v -> {
			result.add(modelMapper.map(v, ResponseOrder.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<ResponseOrder> getOrderByOrderId(
			@PathVariable("orderId") String orderId
			){

		OrderDto orderDto = orderService.getOrderByOrderId(orderId);
		
		ResponseOrder result = modelMapper.map(orderDto, ResponseOrder.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
