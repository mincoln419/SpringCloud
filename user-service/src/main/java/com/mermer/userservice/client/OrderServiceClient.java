package com.mermer.userservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mermer.userservice.vo.ResponseOrder;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderServiceClient {
	
	@GetMapping("/order-service/{userId}/orders_ng")
	List<ResponseOrder> getOrders(@PathVariable String userId);
}
