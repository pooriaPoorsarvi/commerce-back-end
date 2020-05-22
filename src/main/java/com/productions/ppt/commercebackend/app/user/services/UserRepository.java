package com.productions.ppt.commercebackend.app.user.services;

import com.productions.ppt.commercebackend.app.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO move this to the models folder when you make the spring project multi module. Also make
//      checks if you separates the databases
interface UserRepository extends JpaRepository<UserEntity, Integer> {
  @Override
  Optional<UserEntity> findById(Integer ID);

  Optional<UserEntity> findByEmail(String email);
}
