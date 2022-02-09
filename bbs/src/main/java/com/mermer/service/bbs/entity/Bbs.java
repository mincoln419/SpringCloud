package com.mermer.service.bbs.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class) 
public class Bbs {

	@Id @GeneratedValue
	private Long id;
	
	private Integer insterId;
	
	private String title;
	
	private String content;
	
	@CreatedDate
	private LocalDateTime instDtm;

	@Builder.Default
	private Integer readCnt = 0;

	public void updateReadCnt() {
		this.readCnt++;
	}
	
	
}
