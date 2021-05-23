package com.greenfox.greenbay.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users")
public class User {

  @Id
  private String username;
  private String password;
  @OneToMany(mappedBy = "bidder")
  private List<Bid> bids = new ArrayList<>();
  @OneToMany(mappedBy = "seller")
  private List<Item> items = new ArrayList<>();
  private Long balance = 0L;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
