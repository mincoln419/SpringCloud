package com.mermer.userservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.mermer.userservice.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>{

	UserEntity findByEmail(String username);

	UserEntity findAllByUserId(String userId);

}
