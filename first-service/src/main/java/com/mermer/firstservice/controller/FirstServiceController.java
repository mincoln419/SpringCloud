package com.mermer.firstservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/first-service")
@Slf4j
public class FirstServiceController {
	
	Environment env;
	
	public FirstServiceController(Environment env) {
		this.env = env;
	}
	

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to First-Service";
	}
	
	@GetMapping("/message")
	public String message(@RequestHeader("first-request") String header) {
		log.info(header);
		return "Message of First Service";
	}
	
	@GetMapping("/check")
	public String check(HttpServletRequest request) {
		log.info("Server port = {}", request.getServerPort());
		
		
		return String.format("Hi, there. This is a message from First Service %s"
				, env.getProperty("local.server.port"));
	}
}