package com.mermer.service.bbs;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.mermer.service.ServiceApplication;
import com.mermer.service.bbs.entity.Bbs;
import com.mermer.service.bbs.entity.BbsRepository;

@SpringBootTest
public class BbsRepositoryTest {

	@Autowired
	private BbsRepository bbsRepository;
	
	
	@BeforeEach
	public void before() {
	

		
	}
	
	
	@Test
	@DisplayName("bbs list 조회 테스트 성공")
	public void bbsQuery_success() {
		
		String title = "test title";
		Bbs bbs = Bbs.builder()
				.title(title)
				.content("test content")
				.insterId(123)
				.instDtm(LocalDateTime.of(2022,2, 7, 13, 14, 15))
				.build();
		bbsRepository.save(bbs);
		
		List<Bbs> list = bbsRepository.findAll();
		assertThat(list.size()).isGreaterThan(0);
//		assertThat(list.get(0).getTitle()).isEqualTo(title);
	}
}
