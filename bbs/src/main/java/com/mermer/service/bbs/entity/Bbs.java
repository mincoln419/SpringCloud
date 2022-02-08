package com.mermer.service.bbs.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Bbs {

	@Id @GeneratedValue
	private Long id;
	
	private Integer insterId;
	
	private String title;
	
	private String content;
	
	private LocalDateTime instDtm;

	private Integer readCnt;
	
	
}
