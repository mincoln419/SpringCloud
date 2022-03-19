package com.mermer.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.order.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

	OrderEntity findByOrderId(String orderId);
	Iterable<OrderEntity> findByUserId(String userId);
}
