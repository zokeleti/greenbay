package com.greenfox.greenbay.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "items")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToMany(mappedBy = "item")
  private List<Bid> bids = new ArrayList<>();
  private Boolean forSale = false;
  private Boolean sold = false;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private User seller;
  public String sellerName = seller.getUsername();
  private String buyerName;
  private Date biddingDeadline;
  private String name;
  private String description;
  private String picURL;
  private Long startingPrice;
  private Long purchasePrice;

  public Item(String name,
              String description,
              String picURL,
              List<Bid> bids,
              Long purchasePrice,
              String sellerName,
              String buyerName) {
    this.name = name;
    this.description = description;
    this.picURL = picURL;
    this.bids = bids;
    this.purchasePrice = purchasePrice;
    this.sellerName = sellerName;
    this.buyerName = buyerName;
  }

  public Bid getLastOfferedBid(){
    try {
      return bids.get(bids.size() - 1);
    } catch (Exception e) {
      return null;
    }
  }
}
