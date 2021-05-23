package com.greenfox.greenbay.models.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greenfox.greenbay.models.Bid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListedItemDTO {
  private String name;
  private String picURL;
  private Bid lastOfferedBid;
}
