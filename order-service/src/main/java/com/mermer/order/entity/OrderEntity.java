package com.mermer.order.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;

@Data
@Entity
@Table(name = "Orders")
public class OrderEntity implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 120, unique = true)
	private String productId;
	@Column(nullable = false)
	private Integer qty;
	@Column(nullable = false)
	private Integer unitPrice;
	@Column(nullable = false)
	private Integer totalPrice;
	
	
	@Column(nullable = false)
	private String userId;
	@Column(nullable = false, unique = true)	
	private String orderId;
	
	@Column(updatable = false, insertable = true)
	@ColumnDefault(value = "CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

}
