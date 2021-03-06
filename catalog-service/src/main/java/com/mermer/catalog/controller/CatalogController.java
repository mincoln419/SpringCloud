package com.mermer.catalog.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mermer.catalog.entity.CatalogEntity;
import com.mermer.catalog.service.CatalogService;
import com.mermer.catalog.vo.ResponseCatalog;


@RestController
@RequestMapping("/catalog-service")
public class CatalogController {

		@Autowired
		private Environment env;
		
		@Autowired
		private CatalogService catalogService;
		
		@GetMapping("/health_check")
		public String status() {
			return String.format("It's working in Catalog Service on Port %s", env.getProperty("local.server.port"));
		}
		
		@GetMapping("/catalogs")
		public ResponseEntity<List<ResponseCatalog>> getUsers(){
			
			Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();
			
			List<ResponseCatalog> result = new ArrayList<>();
			
			catalogList.forEach(v -> {
				result.add(new ModelMapper().map(v, ResponseCatalog.class));
			});
			
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
		
}
