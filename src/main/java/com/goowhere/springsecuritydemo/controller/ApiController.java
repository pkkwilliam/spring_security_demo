package com.goowhere.springsecuritydemo.controller;

import com.goowhere.springsecuritydemo.ApplicationComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Ka Kei Pun
 * Date: 8/13/20
 * Version: 1.0.0
 */

@RestController
@RequestMapping("/api")
public class ApiController extends ApplicationComponent {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

  @GetMapping("/test1")
  public String test1() {
    return "TEST API 1 " + this.getUsername();
  }

}
