package com.mermer.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mermer.userservice.client.OrderServiceClient;
import com.mermer.userservice.dto.UserDto;
import com.mermer.userservice.entity.UserEntity;
import com.mermer.userservice.repository.UserRepository;
import com.mermer.userservice.vo.ResponseOrder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


	UserRepository userRepository;
	
	BCryptPasswordEncoder passwordEncoder;
	
	RestTemplate restTemplate;
	
	Environment env;
	
	OrderServiceClient client;
	
	CircuitBreakerFactory circuitBreakerFactory;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, 
			BCryptPasswordEncoder passwordEncoder,
			RestTemplate restTemplate,
			Environment env,
			OrderServiceClient client,
			CircuitBreakerFactory circuitBreakerFactory
			) {	
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.restTemplate = restTemplate;
		this.env = env;
		this.client = client;
		this.circuitBreakerFactory = circuitBreakerFactory;
	}
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		userDto.setUserId(UUID.randomUUID().toString());
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
		
		UserEntity newUser = userRepository.save(userEntity);
		
		return mapper.map(newUser, UserDto.class);
	}

	@Override
	public Iterable<UserEntity> getUserByAll() {
		return userRepository.findAll();
	}
	
	
	@Override
	public UserDto getUserById(String userId) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = userRepository.findAllByUserId(userId);
		
		//List<ResponseOrder> orders = new ArrayList();
		UserDto userDto = mapper.map(userEntity, UserDto.class);
		
		/* using a rest-template */
//		String orderUrl = String.format(env.getProperty("order-service.url"), userId);
//		
//		ResponseEntity<List<ResponseOrder>> orderResponse = 
//				restTemplate.exchange(orderUrl, HttpMethod.GET, null, 
//						new ParameterizedTypeReference<List<ResponseOrder>>() {
//						});
//		List<ResponseOrder> orderList = orderResponse.getBody();
		
		/* using Feign Client */
//		// Fegin Exception Handling */
//		List<ResponseOrder> orderList = null;
//		
//		try {
//			orderList = client.getOrders(userId);
//		}catch(FeignException e) {
//			log.error(e.getMessage());
//		}
		
		/* ErrorDecoder */
		//List<ResponseOrder> orderList = client.getOrders(userId);
		
		/* circuitbreaker 사용한 예외 처리 */
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		List<ResponseOrder> orderList  = circuitBreaker.run(() -> client.getOrders(userId)
				, throwable -> new ArrayList<>());
		
		userDto.setOrders(orderList);
		
		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(username);
		
		if(userEntity == null)throw new UsernameNotFoundException(username);
		
		// list에는 권한에 관련된 값이 세팅된다.
		return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailByEmail(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null)throw new UsernameNotFoundException(email);
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		return mapper.map(userEntity, UserDto.class);
	}



	
}
