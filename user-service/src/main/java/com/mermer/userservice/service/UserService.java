package com.mermer.userservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mermer.userservice.dto.UserDto;
import com.mermer.userservice.entity.UserEntity;
import com.mermer.userservice.vo.ResponseUser;

public interface UserService extends UserDetailsService{

	ResponseEntity createUser(UserDto userDto);

	Iterable<UserEntity> getUserByAll();

	
	
}
