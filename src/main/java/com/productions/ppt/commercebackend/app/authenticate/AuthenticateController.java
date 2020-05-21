package com.productions.ppt.commercebackend.app.authenticate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {

    AuthenticationManager authenticationManager;

    public AuthenticateController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/authenticate")
    ResponseEntity<?> authenticate(){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("p@gm.c", "password")
        );
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return ResponseEntity.ok('/');
    }

}
