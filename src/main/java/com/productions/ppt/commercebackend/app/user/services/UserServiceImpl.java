package com.productions.ppt.commercebackend.app.user.services;

import com.productions.ppt.commercebackend.app.user.models.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class UserServiceImpl implements UserService  {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findById(Integer ID){
        return this.userRepository.findById(ID);
    }

    //   TODO add flush to all other repositories
    @Override
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
        userRepository.flush();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
