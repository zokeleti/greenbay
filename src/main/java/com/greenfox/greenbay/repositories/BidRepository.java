package com.greenfox.greenbay.repositories;

import com.greenfox.greenbay.models.Bid;
import org.springframework.data.repository.CrudRepository;

public interface BidRepository extends CrudRepository<Bid, Long> {
}
