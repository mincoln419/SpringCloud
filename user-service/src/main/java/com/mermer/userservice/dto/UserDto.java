package com.mermer.userservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.mermer.userservice.vo.ResponseOrder;

import lombok.Data;

@Data
public class UserDto {

	private String email;
	
	private String name;
	
	private String pwd;
	
	private String userId;
	
	private LocalDateTime createdAt;
	
	private List<ResponseOrder> orders;
	
	private String ecncryptedPwd;
	
}
