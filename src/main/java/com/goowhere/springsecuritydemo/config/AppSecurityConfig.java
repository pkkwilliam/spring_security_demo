package com.goowhere.springsecuritydemo.config;

import com.goowhere.springsecuritydemo.service.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Ka Kei Pun
 * Date: 8/12/20
 * Version: 1.0.0
 */

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  AppUserDetailService appUserDetailService;

  // an interface that need to be implements by us to access db to retrieve user details
  private UserDetailsService userDetailsService;

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    // the object that provide the user auth service
    provider.setUserDetailsService(appUserDetailService);
    provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
    return provider;
  }

  // UserDetailsService is a service to retrieve the user just like the database
//  @Bean
//  @Override
//  protected UserDetailsService userDetailsService() {
//
//    List<UserDetails> users = new ArrayList<>();
//    users.add(
//            UserDao
//                    .withDefaultPasswordEncoder()
//                    .username("pkk")
//                    .password("123")
//                    .roles("USER")
//                    .build());
//
//    return new InMemoryUserDetailsManager(users);
//  }
}
