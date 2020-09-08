package com.productions.ppt.commercebackend.app.banner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<BannerEntity, Integer> {
    @Override
    Optional<BannerEntity> findById(Integer integer);

    @Override
    List<BannerEntity> findAll();
}
