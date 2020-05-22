package com.productions.ppt.commercebackend.app.user.services;

import com.productions.ppt.commercebackend.app.user.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
  Optional<UserEntity> findById(Integer ID);

  void save(UserEntity userEntity);

  Optional<UserEntity> findByEmail(String email);

}
