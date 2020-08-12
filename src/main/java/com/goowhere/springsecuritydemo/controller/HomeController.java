package com.goowhere.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Ka Kei Pun
 * Date: 8/12/20
 * Version: 1.0.0
 */

@RestController
public class HomeController {

  @RequestMapping("/home")
  public String home() {
    return "home";
  }

}
