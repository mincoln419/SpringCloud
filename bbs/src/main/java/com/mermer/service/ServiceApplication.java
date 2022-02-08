package com.mermer.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	
	@Bean
	public ModelMapper modelMapper() {
		//모델 매핑 정책 : strict -> dto -> entity layer 변경시 없는 필드에 대해서는 entity를 엄격히 따름
		//STANDARD -> 글자가 포함됐을 경우(id 가 포함된 loginId -> id) 매핑된다... 
		//이름이 겹쳐지는 필드일 경우에는 Strict로 구성해야 함
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
			.setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
	}
}
