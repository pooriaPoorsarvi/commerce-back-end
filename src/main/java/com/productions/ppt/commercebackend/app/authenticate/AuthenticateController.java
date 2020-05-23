package com.productions.ppt.commercebackend.app.authenticate;

import com.productions.ppt.commercebackend.config.security.UsersConfiguration.GeneralUserDetailsService;
import com.productions.ppt.commercebackend.exceptions.AuthenticationError;
import com.productions.ppt.commercebackend.shared.utils.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
class AuthenticateController {

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


  @CrossOrigin()
  @PostMapping("/authenticate")
  ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticateInput authenticateInput) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authenticateInput.email, authenticateInput.password));
    } catch (AuthenticationException e) {
      throw new AuthenticationError("Bad Credentials was entered.");
    }
    AuthenticateOutput authenticateOutput =
        new AuthenticateOutput(
            jwtUtil.generateToken(generalUserDetailsService.loadUserByUsername(authenticateInput.email)));
    return ResponseEntity.ok(authenticateOutput);
  }
}
