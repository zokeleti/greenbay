package com.greenfox.greenbay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToMany(mappedBy = "item")
  private List<Bid> bids;
  private Boolean forSale;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User owner;
  private Date biddingDeadline;
  private String name;
  private String description;
  private String picURL;
  private Long startingPrice;
  private Long purchasePrice;
}
