package com.goowhere.springsecuritydemo.model;

/**
 * Author: Ka Kei Pun
 * Date: 8/13/20
 * Version: 1.0.0
 */

public class LoginRequestModel {

  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "LoginRequestModel{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}
