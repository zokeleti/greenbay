package com.greenfox.greenbay.controllers;

import com.greenfox.greenbay.models.DTOs.LoginRequestDTO;
import com.greenfox.greenbay.models.DTOs.LoginResponseDTO;
import com.greenfox.greenbay.models.Item;
import com.greenfox.greenbay.models.User;
import com.greenfox.greenbay.models.exceptions.InvalidPriceException;
import com.greenfox.greenbay.models.exceptions.InvalidURLException;
import com.greenfox.greenbay.models.exceptions.MissingFieldException;
import com.greenfox.greenbay.security.JwtUtil;
import com.greenfox.greenbay.services.ItemService;
import com.greenfox.greenbay.services.MyUserDetailsService;
import com.greenfox.greenbay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final ItemService itemService;
  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final MyUserDetailsService myUserDetailsService;
  private final JwtUtil jwtUtil;

  @Autowired
  public UserController(ItemService itemService, UserService userService,
                        AuthenticationManager authenticationManager,
                        MyUserDetailsService myUserDetailsService, JwtUtil jwtUtil) {
    this.itemService = itemService;
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.myUserDetailsService = myUserDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/create")
  public ResponseEntity<Item> createItem(@RequestBody Item item)
      throws MissingFieldException, InvalidPriceException, InvalidURLException {
    User user = new User("username", "password");
    userService.saveUser(user);
    item.setOwner(user);
    Item newItem = itemService.createItem(item);
    return ResponseEntity.ok(newItem);
  }

  @PostMapping("/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDTO authenticationRequest) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect username or password", e);
    }
    final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new LoginResponseDTO(jwt));
  }
}
