package com.mermer.userservice.service;

import org.springframework.http.ResponseEntity;

import com.mermer.userservice.dto.UserDto;

public interface UserService {

	ResponseEntity createUser(UserDto userDto);
	
}
