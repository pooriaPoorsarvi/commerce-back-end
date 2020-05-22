package com.productions.ppt.commercebackend.config.security.UsersConfiguration;

import com.productions.ppt.commercebackend.app.user.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
class GeneralUserDetailsServiceImpl implements GeneralUserDetailsService {

  UserService userService;

  public GeneralUserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    GeneralUserDetails res =  new GeneralUserDetails(
        userService
            .findByEmail(s)
            .<UsernameNotFoundException>orElseThrow(
                () -> {
                  throw new UsernameNotFoundException("User name not found.");
                }));
    return res;
  }
}
