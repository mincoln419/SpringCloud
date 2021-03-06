package com.mermer.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.userservice.dto.UserDto;
import com.mermer.userservice.entity.UserEntity;
import com.mermer.userservice.service.UserService;
import com.mermer.userservice.vo.Greeting;
import com.mermer.userservice.vo.ResponseUser;

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
		return String.format("It's working in User Service"
				+", port(local.server.port)=" + env.getProperty("local.server.port")
				+", port(server.port)=" + env.getProperty("server.port")
				+", token_secret=" + env.getProperty("token.secret")
				+", token_expiration time=" + env.getProperty("token.expiration_time")
				
				);
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		log.info("greeting :: {}",  greeting.getMessage());
		return env.getProperty("greeting.message");
	}
	
	@PostMapping("/users")
	public ResponseEntity createUser(@RequestBody UserDto userDto) {
	
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		userService.createUser(userDto);
		ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<ResponseUser>> getUsers(){
		
		Iterable<UserEntity> userList = userService.getUserByAll();
		
		List<ResponseUser> result = new ArrayList<>();
		
		userList.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseUser.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseUser> getUser(@PathVariable String userId){
		
		UserDto userDto = userService.getUserById(userId);
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ResponseUser result = mapper.map(userDto, ResponseUser.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseUser> login(){
		
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
