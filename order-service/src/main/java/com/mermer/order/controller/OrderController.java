package com.mermer.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.mermer.order.messagequeue.OrderProducer;
import com.mermer.order.service.OrderService;
import com.mermer.order.vo.RequestOrder;
import com.mermer.order.vo.ResponseOrder;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order-service")
@Slf4j
public class OrderController {

	private Environment env;
	
	private OrderService orderService;
	
	private ModelMapper modelMapper;
	
	private KafkaProducer kafkaProducer;
	
	private OrderProducer orderProducer; 
	
	@Autowired
	public OrderController(Environment env, 
			OrderService orderService, 
			ModelMapper modelMapper, 
			KafkaProducer kafkaProducer,
			OrderProducer orderProducer
			) {
		this.env = env;
		this.orderService = orderService;
		this.modelMapper = modelMapper;
		this.kafkaProducer = kafkaProducer;
		this.orderProducer = orderProducer;
	}

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's working in Order Service on Port %s", env.getProperty("local.server.port"));
	}
	
	
	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(
			@PathVariable("userId") String userId,
			@RequestBody RequestOrder requestOrder){
		
		log.info("Before add order data");
		OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
		orderDto.setUserId(userId);
		/* jpa */
		OrderDto createOrderDto = orderService.createOrder(orderDto);
		ResponseOrder responseOrder = modelMapper.map(createOrderDto, ResponseOrder.class);
		
		/* kafka */
//		orderDto.setOrderId(UUID.randomUUID().toString());
//		orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());
		
		
		/* send this order to the kafka*/
//		kafkaProducer.send("example-catalog-topic", orderDto);
//		orderProducer.send("orders", orderDto);
		
//		ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);
		
		log.info("After added order data");
		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}
	
	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) throws Exception{
		log.info("Before retrieve order data");
		Iterable<OrderEntity> orderList = orderService.getOrderByUserId(userId);
		
		List<ResponseOrder> result = new ArrayList<>();
		orderList.forEach(v -> {
			result.add(modelMapper.map(v, ResponseOrder.class));
		});
		
		try {
			Thread.sleep(1000);
			throw new Exception("장애 발생");
		}catch(InterruptedException e) {
			log.error(e.getMessage());
		}
		
		log.info("After retrieved order data");
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
