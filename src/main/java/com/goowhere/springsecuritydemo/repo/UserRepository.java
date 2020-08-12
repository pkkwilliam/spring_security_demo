package com.goowhere.springsecuritydemo.repo;

import com.goowhere.springsecuritydemo.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Ka Kei Pun
 * Date: 8/12/20
 * Version: 1.0.0
 */

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long> {

  UserDao findByUsername(String username);

}
