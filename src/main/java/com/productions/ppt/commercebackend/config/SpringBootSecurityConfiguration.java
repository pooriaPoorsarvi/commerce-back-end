package com.productions.ppt.commercebackend.config;

import com.productions.ppt.commercebackend.app.user.GeneralUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringBootSecurityConfiguration extends WebSecurityConfigurerAdapter {

  GeneralUserDetailsService generalUserDetailsService;

  SpringBootSecurityConfiguration(GeneralUserDetailsService generalUserDetailsService) {
    this.generalUserDetailsService = generalUserDetailsService;
  }

//  TODO delete unnecessary crossOrigin annotations
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .cors()
        .and()
        .authorizeRequests()
        .antMatchers("/users/**")
        .authenticated()
        .antMatchers("/authenticate")
        .permitAll()
        .antMatchers("/**")
        .permitAll()
        .and()
        .formLogin();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(generalUserDetailsService);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }
}
