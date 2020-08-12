package com.goowhere.springsecuritydemo.service;

import com.goowhere.springsecuritydemo.model.UserDao;
import com.goowhere.springsecuritydemo.model.UserPrincipal;
import com.goowhere.springsecuritydemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Author: Ka Kei Pun
 * Date: 8/12/20
 * Version: 1.0.0
 */

@Service
public class AppUserDetailService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDao userDAO = userRepository.findByUsername(username);
    if (userDAO == null) {
      throw new UsernameNotFoundException("userDAO 404");
    }
    return new UserPrincipal(userDAO);
  }

}
