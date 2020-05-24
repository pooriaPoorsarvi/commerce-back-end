package com.productions.ppt.commercebackend.config.security.filters;

import com.productions.ppt.commercebackend.config.security.UsersConfiguration.GeneralUserDetailsService;
import com.productions.ppt.commercebackend.shared.utils.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

  GeneralUserDetailsService generalUserDetailsService;

  JWTUtil jwtUtil;

  public JWTRequestFilter(GeneralUserDetailsService generalUserDetailsService, JWTUtil jwtUtil) {
    this.generalUserDetailsService = generalUserDetailsService;
    this.jwtUtil = jwtUtil;
  }

  void validateJWT(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {

    final String authorizationHeader = httpServletRequest.getHeader("Authorization");

    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      System.out.println("JWT : " + authorizationHeader.substring(7));
      jwt = authorizationHeader.substring(7);
      username = jwtUtil.extractUsername(jwt);
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = generalUserDetailsService.loadUserByUsername(username);

      if (jwtUtil.validateToken(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        //        TODO check if authentication manager does the same on authenticate
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {
    try {
      validateJWT(httpServletRequest, httpServletResponse, filterChain);
      //          TODO make the exception more specific : check if you need to add
      // IllegalArgumentException as well
    } catch (ExpiredJwtException
        | UnsupportedJwtException
        | MalformedJwtException
        | SignatureException
        | UsernameNotFoundException e) {
      //        TODO make this into a class in the future, and make the error an actual error
      httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
      Map<String, String> res = new HashMap<>();
      res.put("\"error\"", "\"Bad JWT\"");
      res.put("\"message\"", "\"Bad JWT was received.\"");
      res.put("\"path\"", "\""+httpServletRequest.getServletPath()+"\"");
      res.put("\"status\"", "401");
      httpServletResponse.setStatus(401);
      httpServletResponse.setHeader("Content-Type", "application/json");
      httpServletResponse.getWriter().write(res.toString());
    }
  }
}
