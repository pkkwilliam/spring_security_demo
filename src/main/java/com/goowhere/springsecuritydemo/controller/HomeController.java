package com.goowhere.springsecuritydemo.controller;

import com.goowhere.springsecuritydemo.model.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Author: Ka Kei Pun
 * Date: 8/12/20
 * Version: 1.0.0
 */

@RestController
public class HomeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

  @RequestMapping("/home")
  public String home() {
    final UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    LOGGER.info(principal.toString());
    return "home " + principal.getUsername();
  }

  @RequestMapping("/login")
  public String login(@RequestParam String username, String password) {
    return "login";
  }

  @RequestMapping("/logout")
  public String logout() {
    return "ogout";
  }

}
