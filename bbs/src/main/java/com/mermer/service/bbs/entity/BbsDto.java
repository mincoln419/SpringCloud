package com.mermer.service.bbs.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BbsDto {
	
	private Integer insterId;
	
	private String title;
	
	private String content;
}
