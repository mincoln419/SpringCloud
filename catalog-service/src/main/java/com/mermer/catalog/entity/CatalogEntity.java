package com.mermer.catalog.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "catalog")
public class CatalogEntity implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 120, unique = true)
	private String productId;
	@Column(nullable = false)
	private String productName;
	@Column(nullable = false)	
	private Integer stock;
	@Column(nullable = false)
	private Integer unitPrice;
	
	@Column(nullable = false, updatable = false, insertable = true)
	@ColumnDefault(value = "CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
}
