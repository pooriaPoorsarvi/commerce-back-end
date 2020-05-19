package com.productions.ppt.commercebackend.app.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Override
    Optional<UserEntity> findById(Integer ID);

    Optional<UserEntity> findByEmail(String email);
}
