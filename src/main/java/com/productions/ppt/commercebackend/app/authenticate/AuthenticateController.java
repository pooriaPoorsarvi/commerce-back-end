package com.productions.ppt.commercebackend.app.authenticate;

import com.productions.ppt.commercebackend.app.user.GeneralUserDetailsService;
import com.productions.ppt.commercebackend.exceptions.AuthenticationError;
import com.productions.ppt.commercebackend.shared.utils.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {

  AuthenticationManager authenticationManager;
  GeneralUserDetailsService generalUserDetailsService;
  JWTUtil jwtUtil;

  public AuthenticateController(
      AuthenticationManager authenticationManager,
      GeneralUserDetailsService generalUserDetailsService,
      JWTUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.generalUserDetailsService = generalUserDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @GetMapping("/authenticate")
  ResponseEntity<?> authenticate() {
    try{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("p@gm.c1", "password"));
    }catch (AuthenticationException e){
        throw new AuthenticationError("Bad Credentials was entered.");
    }
    AuthenticateOutput authenticateOutput =
        new AuthenticateOutput(
            jwtUtil.generateToken(generalUserDetailsService.loadUserByUsername("p@gm.c")));
    return ResponseEntity.ok(authenticateOutput);
  }
}
