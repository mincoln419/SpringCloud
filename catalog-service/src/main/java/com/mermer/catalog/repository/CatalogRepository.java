package com.mermer.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermer.catalog.entity.CatalogEntity;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long>{

	CatalogEntity findByProductId(String productId);
}
