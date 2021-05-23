package com.greenfox.greenbay.repositories;

import com.greenfox.greenbay.models.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
  List<Item> findAllBySold(Boolean sold, Pageable pageable);
  Optional<Item> findById(Long id);
}
