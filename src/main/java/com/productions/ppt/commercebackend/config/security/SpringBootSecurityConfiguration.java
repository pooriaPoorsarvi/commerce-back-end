package com.productions.ppt.commercebackend.config.security;

import com.productions.ppt.commercebackend.config.security.UsersConfiguration.GeneralUserDetailsService;
import com.productions.ppt.commercebackend.config.security.filters.JWTRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SpringBootSecurityConfiguration extends WebSecurityConfigurerAdapter {

  GeneralUserDetailsService generalUserDetailsService;

  JWTRequestFilter jwtRequestFilter;

  SpringBootSecurityConfiguration(
      GeneralUserDetailsService generalUserDetailsService, JWTRequestFilter jwtRequestFilter) {
    this.generalUserDetailsService = generalUserDetailsService;
    this.jwtRequestFilter = jwtRequestFilter;
  }

  //  TODO delete unnecessary crossOrigin annotations
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //    TODO in the future only allow unauthorized people to log back in
    //    TODO check if allowing all options is ok
//  TODO factor out the OPTIONS
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS, "*")
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/*")
        .permitAll()
        .antMatchers("/products/admin")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.OPTIONS, "*")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/products/*")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/products/*")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/categories/*")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/categories/*")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/banner/*")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/banner/*")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/banner/*")
        .permitAll()
        .antMatchers(HttpMethod.PUT, "/categories/*")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/products/all")
        .hasRole("ADMIN")
        .antMatchers("/order/*")
        .authenticated()
        //        TODO check if this is nesc later
        .antMatchers(HttpMethod.GET, "/users")
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/users")
        .permitAll()
        .antMatchers("/users")
        .authenticated()
        .antMatchers(HttpMethod.OPTIONS, "/users/*")
        .permitAll()
        .antMatchers("/users/*")
        .authenticated()
        .antMatchers("/authenticate")
        .permitAll()
        .antMatchers("/*")
        .permitAll()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(generalUserDetailsService);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }
}
