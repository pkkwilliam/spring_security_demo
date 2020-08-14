package com.goowhere.springsecuritydemo.config;

import com.goowhere.springsecuritydemo.repo.UserRepository;
import com.goowhere.springsecuritydemo.service.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Author: Ka Kei Pun
 * Date: 8/12/20
 * Version: 1.0.0
 */

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

  AppUserDetailService appUserDetailService;
  UserRepository userRepository;

  // an interface that need to be implements by us to access db to retrieve user details
  private UserDetailsService userDetailsService;

  public AppSecurityConfig(AppUserDetailService appUserDetailService, UserRepository userRepository) {
    this.appUserDetailService = appUserDetailService;
    this.userRepository = userRepository;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    // the object that provide the user auth service
    provider.setUserDetailsService(appUserDetailService);
    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    return provider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // add jwt filter (1, authentication, 2 authorization)
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
            .authorizeRequests()
            // configure access rules
            .antMatchers("/login").permitAll()
            .antMatchers("/api/**").hasRole("USER");
  }

  // V2
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//            .authorizeRequests()
//            .antMatchers("/index.html").permitAll()
//            .antMatchers("/profile/**").authenticated()
//            .antMatchers("/admin/**").hasRole("ADMIN")
//            .antMatchers("/management/**").hasAnyRole("ADMIN", "MANAGER")
//            .antMatchers("/api/**").hasRole("USER")
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .httpBasic();
//  }

  // V1
  //  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//            .csrf().disable()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.POST, "/secure").authenticated()
//            .anyRequest().permitAll()
//            .and()
//            .logout().invalidateHttpSession(true)
//            .clearAuthentication(true)
////            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
//            .logoutUrl("/logout");
//  }

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
