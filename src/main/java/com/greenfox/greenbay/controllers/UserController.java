package com.greenfox.greenbay.controllers;

import com.greenfox.greenbay.models.DTOs.LoginRequestDTO;
import com.greenfox.greenbay.models.DTOs.LoginResponseDTO;
import com.greenfox.greenbay.models.DTOs.ResponseDTO;
import com.greenfox.greenbay.models.Item;
import com.greenfox.greenbay.models.User;
import com.greenfox.greenbay.models.exceptions.InvalidPriceException;
import com.greenfox.greenbay.models.exceptions.InvalidURLException;
import com.greenfox.greenbay.models.exceptions.ItemNotFoundException;
import com.greenfox.greenbay.models.exceptions.MissingFieldException;
import com.greenfox.greenbay.models.exceptions.UserNotFoundException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<Item> createItem(@RequestBody Item item,  @RequestHeader("Authorization") String header)
      throws MissingFieldException, InvalidPriceException, InvalidURLException, UserNotFoundException {
    User user = userService.findByUsername(jwtUtil.extractUsername(header.split(" ")[1]));
    item.setSeller(user);
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
    String username = authenticationRequest.getUsername();
    final UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
    final String jwt = jwtUtil.generateToken(userDetails);
    LoginResponseDTO response = new LoginResponseDTO(jwt);
    response.setBalance(userService.findByUsername(username).getBalance());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/list")
  public ResponseEntity<?> listItems(@RequestParam(required = false) Integer page) {
    if(page == null){
      return ResponseEntity.ok(itemService.listItemsByPage(0));
    }
    if(page < 1){
      return ResponseEntity.badRequest().body(new ResponseDTO("Page number must be positive whole number"));
    }
    return ResponseEntity.ok(itemService.listItemsByPage(page - 1));
  }

  @GetMapping("/item")
  public ResponseEntity<?> getItem(@RequestParam(required = false) Long id) throws ItemNotFoundException {
    if(id == null){
      return ResponseEntity.badRequest().body(new ResponseDTO("'id' parameter is missing"));
    }
    return ResponseEntity.ok(itemService.findById(id));
  }

}
