package com.productions.ppt.commercebackend.app.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GeneralUserDetailsService implements UserDetailsService {

  UserRepository userRepository;

  public GeneralUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    GeneralUserDetails res =  new GeneralUserDetails(
        userRepository
            .findByEmail(s)
            .<UsernameNotFoundException>orElseThrow(
                () -> {
                  throw new UsernameNotFoundException("User name not found.");
                }));
    return res;
  }
}
