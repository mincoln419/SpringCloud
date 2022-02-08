package com.mermer.service.bbs;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mermer.service.bbs.entity.Bbs;
import com.mermer.service.bbs.entity.BbsDto;
import com.mermer.service.bbs.entity.BbsRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BbsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BbsRepository bbsRepository;
	
	@Autowired
	protected ObjectMapper objMapper;
	
	@BeforeEach
	public void before() {
		
		bbsRepository.deleteAll();
		
	}
	
	@Test
	@DisplayName("bbs 목록조회 mvc 테스트")
	public void getBbsQuery() throws Exception {
		
		
		String title = "test title";
		Bbs bbs = Bbs.builder()
				.title(title)
				.content("test content")
				.insterId(123)
				.instDtm(LocalDateTime.of(2022,2, 7, 13, 14, 15))
				.readCnt(2)
				.build();
		bbsRepository.save(bbs);
		
		mockMvc.perform(get("/api/bbs")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	
	
	@Test
	@DisplayName("bbs 상세조회 mvc 테스트")
	public void selectOneBbs_success() throws Exception {
		
		
		String title = "test title";
		Bbs bbs = Bbs.builder()
				.title(title)
				.content("test content")
				.insterId(123)
				.instDtm(LocalDateTime.of(2022,2, 7, 13, 14, 15))
				.readCnt(2)
				.build();
		bbsRepository.save(bbs);
		
		mockMvc.perform(get("/api/bbs/{id}",bbs.getId())
				.accept(MediaType.APPLICATION_JSON)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("title").value(title));
	}
	
	
	@Test
	@DisplayName("bbs 생성 mvc 테스트")
	public void createBbs_success() throws Exception {
		
		
		String title = "test title";
		Bbs bbs = Bbs.builder()
				.title(title)
				.content("test content")
				.insterId(123)
				.instDtm(LocalDateTime.of(2022,2, 7, 13, 14, 15))
				.readCnt(2)
				.build();
		
		mockMvc.perform(post("/api/bbs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(bbs))
				.accept(MediaType.APPLICATION_JSON)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("title").value(title));
	}
	
	@Test
	@DisplayName("bbs 수정 mvc 테스트")
	public void updateBbs_success() throws Exception {
		
		
		String title = "test title";
		String content = "test content";
		Bbs bbs = Bbs.builder()
				.title(title)
				.content(content)
				.insterId(123)
				.instDtm(LocalDateTime.of(2022,2, 7, 13, 14, 15))
				.readCnt(2)
				.build();
		
		bbsRepository.save(bbs);
		
		
		BbsDto bbsUpdate = BbsDto.builder()
				.title(title + "modified")
				.insterId(123)
				.build();
		
		mockMvc.perform(put("/api/bbs/{id}", bbs.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(bbsUpdate))
				.accept(MediaType.APPLICATION_JSON)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("content").value(content))
		.andExpect(jsonPath("title").value(title + "modified"));
	}
	
}
