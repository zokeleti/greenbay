package com.greenfox.greenbay.controllers;

import com.greenfox.greenbay.models.Item;
import com.greenfox.greenbay.models.User;
import com.greenfox.greenbay.services.ItemService;
import com.greenfox.greenbay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final ItemService itemService;
  private final UserService userService;

  @Autowired
  public UserController(ItemService itemService, UserService userService) {
    this.itemService = itemService;
    this.userService = userService;
  }

  @PostMapping("/create")
  public ResponseEntity<Item> createItem(@RequestBody Item item){
    User user = new User("username", "password");
    item.setOwner(user);
    user.getItems().add(item);
    userService.saveUser(user);
    return ResponseEntity.ok(itemService.saveItem(item));
  }
}
