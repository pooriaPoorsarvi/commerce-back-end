package com.productions.ppt.commercebackend.app.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("users/{ID}")
    UserEntity getUser(@PathVariable Integer ID){
        return this.userRepository.findById(ID).orElse(null);
    }

}
