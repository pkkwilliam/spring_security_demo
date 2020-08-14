package com.goowhere.springsecuritydemo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Author: Ka Kei Pun
 * Date: 8/14/20
 * Version: 1.0.0
 */

public class ApplicationComponent {


  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public String getUsername() {
    return (String) getAuthentication().getPrincipal();
  }

}
