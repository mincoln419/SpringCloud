package com.mermer.service.biz;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mermer.service.bbs.entity.Bbs;
import com.mermer.service.bbs.entity.BbsDto;
import com.mermer.service.bbs.entity.BbsRepository;

@Service
public class BbsService {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private BbsRepository bbsRepository;

	
	public ResponseEntity getBbsQuery() {

		List<Bbs> list = bbsRepository.findAll();
		return ResponseEntity.ok(list);
	}


	public ResponseEntity selectOneBbs(Long id) {
		
		Bbs bbs = bbsRepository.findById(id).get();
		
		bbs.updateReadCnt();
		
		bbsRepository.save(bbs);
		
		return ResponseEntity.ok(bbs);
	}


	public ResponseEntity createBbs(Bbs bbs) {

		Bbs bbsResult =  bbsRepository.save(bbs);
		
		return ResponseEntity.ok(bbsResult);
	}


	public ResponseEntity updateBbs(Long id, BbsDto bbsDto) {
		
		Optional<Bbs> bbsOpt = bbsRepository.findById(id);
		
		if(bbsOpt.isEmpty()) return ResponseEntity.notFound().build();
		
		Bbs bbs = bbsOpt.get();
		mapper.map(bbsDto, bbs);
		
		Bbs bbsResult =  bbsRepository.save(bbs);
		
		return ResponseEntity.ok(bbsResult);
	}


	public ResponseEntity deleteBbs(Long id) {
		Optional<Bbs> bbsOpt = bbsRepository.findById(id);
		
		if(bbsOpt.isEmpty()) return ResponseEntity.notFound().build();
		
		
		bbsRepository.delete(bbsOpt.get());
		return ResponseEntity.ok().build();
	}
	
	
	
}
