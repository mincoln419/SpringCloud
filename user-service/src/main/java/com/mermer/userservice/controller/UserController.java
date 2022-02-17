package com.mermer.userservice.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.userservice.dto.UserDto;
import com.mermer.userservice.entity.UserEntity;
import com.mermer.userservice.service.UserService;
import com.mermer.userservice.vo.Greeting;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/")
public class UserController {

	private Environment env;
	
	private UserService userService;
	
	@Autowired
	private Greeting greeting;
	
	@Autowired
	public UserController(Environment env, UserService userService) {
		this.env = env;
		this.userService = userService;
	}
	
	
	@GetMapping("/health_check")
	public String status() {
		return "It's working in User Service";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		log.info("greeting :: {}",  greeting.getMessage());
		return env.getProperty("greeting.message");
	}
	
	@PostMapping("/users")
	public ResponseEntity createUser(@RequestBody UserEntity user) {
	
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDto userDto = mapper.map(user, UserDto.class);
		userService.createUser(userDto);
		return new ResponseEntity(HttpStatus.CREATED);
	}
}
