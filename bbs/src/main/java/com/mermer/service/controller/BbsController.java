package com.mermer.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.service.bbs.entity.Bbs;
import com.mermer.service.bbs.entity.BbsDto;
import com.mermer.service.biz.BbsService;

@RestController
@RequestMapping(value = "/api/bbs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BbsController {

	@Autowired
	private BbsService bbsService;

	@SuppressWarnings("rawtypes")
	@GetMapping
	public ResponseEntity getBbsQuery() {

		ResponseEntity responseEntity = bbsService.getBbsQuery();

		return responseEntity;
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/{id}")
	public ResponseEntity selectOneBbs(@PathVariable Long id) {

		ResponseEntity responseEntity = bbsService.selectOneBbs(id);

		return responseEntity;
	}

	@SuppressWarnings("rawtypes")
	@PostMapping
	public ResponseEntity createBbs(@RequestBody Bbs bbs) {

		ResponseEntity responseEntity = bbsService.createBbs(bbs);

		return responseEntity;
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/{id}")
	public ResponseEntity updateBbs(@PathVariable Long id, @RequestBody BbsDto bbs) {

		ResponseEntity responseEntity = bbsService.updateBbs(id, bbs);

		return responseEntity;
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	public ResponseEntity deleteBbs(@PathVariable Long id) {

		ResponseEntity responseEntity = bbsService.deleteBbs(id);

		return responseEntity;
	}
}
