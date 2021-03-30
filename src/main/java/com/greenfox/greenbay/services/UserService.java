package com.greenfox.greenbay.services;

import com.greenfox.greenbay.models.User;
import com.greenfox.greenbay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  public User saveUser(User user){
    return userRepository.save(user);
  }
}
