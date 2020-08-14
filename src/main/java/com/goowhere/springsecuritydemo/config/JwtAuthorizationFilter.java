package com.goowhere.springsecuritydemo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.goowhere.springsecuritydemo.model.UserDao;
import com.goowhere.springsecuritydemo.model.UserPrincipal;
import com.goowhere.springsecuritydemo.repo.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Ka Kei Pun
 * Date: 8/13/20
 * Version: 1.0.0
 */

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private
  UserRepository userRepository;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
    super(authenticationManager);
    this.userRepository = userRepository;
  }

  /**
   * to filter out if this is a secure user
   * @param request
   * @param response
   * @param chain
   * @throws IOException
   * @throws ServletException
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

    // Read the authorization header, where the jwt token should be
    String header = request.getHeader(JwtProperties.HEADER_STRING);

    // If header does not container Bearer or is null delegate spring implementation then return
    if (header == null) {
      chain.doFilter(request, response);
      return;
    }

    // If header is present, try grab user principle from db and perform authorization
    Authentication authentication = getUsernameAndPassword(request);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Continue filter execution
    chain.doFilter(request, response);

  }

  private Authentication getUsernameAndPassword(HttpServletRequest request) {
    final String token = request.getHeader(JwtProperties.HEADER_STRING);
    if (token != null) {
      // parse the token and validate it
      String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
              .build()
              .verify(token.replace(JwtProperties.TOKEN_PREFIX, ""))
              .getSubject();

      // Search in the DB if we find the user by token subject(username)
      // If so, then grab user details and create spring aith token using username, password, authorities/roles
      if (username  != null) {
        UserDao user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userPrincipal.getAuthorities());
        return authenticationToken;
      }
    }
    return null;
  }

}
