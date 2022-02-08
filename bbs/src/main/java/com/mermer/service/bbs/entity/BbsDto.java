package com.mermer.service.bbs.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class BbsDto {
	
	private Integer insterId;
	
	private String title;
	
	private String content;
}
