package com.mermer.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.mermer.service.bbs.entity.Bbs;
import com.mermer.service.bbs.entity.BbsRepository;

@SpringBootApplication
@EnableJpaAuditing
public class ServiceApplication {
	
	@Autowired
	BbsRepository bbsRepository;

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
	
	@Bean
	public void initData() {
		
		String[] texts = {"test", "dummy", "idea", "tec"};
		
		
		
		String title = "test title";
		String content = "test content";
	
		
		for(int i = 0 ; i < 20 ; i++) {
			int randInt = (int)(Math.random()*10) % 4;
			Bbs bbs = Bbs.builder()
					.title(texts[randInt])
					.content(texts[randInt] + "contents")
					.insterId(randInt*3)
					.instDtm(LocalDateTime.of(2022,2, 7, 13, 14, 15))
					.readCnt(randInt)
					.build();
			
			bbsRepository.save(bbs);
		}
		
	}
}
