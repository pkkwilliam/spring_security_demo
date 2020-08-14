package com.goowhere.springsecuritydemo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goowhere.springsecuritydemo.model.LoginRequestModel;
import com.goowhere.springsecuritydemo.model.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Author: Ka Kei Pun
 * Date: 8/13/20
 * Version: 1.0.0
 */

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  /**
   * Trigger when we issue POST request to /login
   * we need to pass in a body object of LoginRequestModel
   * @param request
   * @param response
   * @return
   * @throws AuthenticationException
   */
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    // Grab credentials and map them to LoginRequestModel
    LoginRequestModel credentials = null;
    try {
      // this get the raw request from the request
      credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
      LOGGER.info("Login Request:{}",credentials);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Create login token
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            credentials.getUsername(),
            credentials.getPassword(),
            new ArrayList<>()
        );

    // Authenticate user
    Authentication authentication = authenticationManager.authenticate(authenticationToken);
    return authentication;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    //  Grab principal
    UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();

    // Create JWT
    String token = JWT.create()
            .withSubject(userPrincipal.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPRATION))
            .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));

    // add token into response
    response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
  }

}
