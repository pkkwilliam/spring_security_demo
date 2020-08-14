package com.goowhere.springsecuritydemo.config;

/**
 * Author: Ka Kei Pun
 * Date: 8/13/20
 * Version: 1.0.0
 */

public class JwtProperties {

  public static final String SECRET = "123"; //HASH
  public static final int EXPRATION = 864000000; // 10 days
  public static final String TOKEN_PREFIX =  "Bearer ";
  public static final String HEADER_STRING = "Authorization";

}
