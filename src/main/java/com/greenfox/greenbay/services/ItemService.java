package com.greenfox.greenbay.services;

import com.greenfox.greenbay.models.DTOs.ListedItemDTO;
import com.greenfox.greenbay.models.Item;
import com.greenfox.greenbay.models.exceptions.InvalidPriceException;
import com.greenfox.greenbay.models.exceptions.InvalidURLException;
import com.greenfox.greenbay.models.exceptions.ItemNotFoundException;
import com.greenfox.greenbay.models.exceptions.MissingFieldException;
import com.greenfox.greenbay.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
  @Autowired
  ItemRepository itemRepository;

  public Item createItem(Item item) throws InvalidURLException, MissingFieldException, InvalidPriceException {
    if(checkNullFields(item).size() > 0){
      throw new MissingFieldException(checkNullFields(item).toString());
    }
    if(!isValidURL(item.getPicURL())){
      throw new InvalidURLException("Url is invalid");
    }
    if(item.getPurchasePrice() < 1 || item.getStartingPrice() < 1){
      throw new InvalidPriceException("The prices must be positive whole numbers");
    }
    return saveItem(item);
  }

  public Item saveItem(Item item) {
    return itemRepository.save(item);
  }

  public boolean isValidURL(String url) {
    try {
      new URL(url).toURI();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public List<String> checkNullFields(Item item){
    List<String> nullFields = new ArrayList<>();
    if(item.getName() == null){nullFields.add("Name");}
    if(item.getDescription() == null){nullFields.add("Description");}
    if(item.getPicURL() == null){nullFields.add("PicUrl");}
    if(item.getStartingPrice() == null){nullFields.add("startingPrice");}
    if(item.getPurchasePrice() == null){nullFields.add("purchasePrice");}
    return nullFields;
  }

  public List<ListedItemDTO> listItemsByPage(Integer page){
    Pageable requested = PageRequest.of(page, 20);
    List<Item> itemList = itemRepository.findAllBySold(false, requested);
    List<ListedItemDTO> result = itemList
        .stream()
        .map(i -> new ListedItemDTO(i.getName(), i.getPicURL(), i.getLastOfferedBid()))
        .collect(Collectors.toList());
    return result;
  }

  public Item findById(Long id) throws ItemNotFoundException {
    Optional<Item> optionalItem = itemRepository.findById(id);
    if(!optionalItem.isPresent()){
      throw new ItemNotFoundException("There is no item in the database with the given id");
    }
    Item match = optionalItem.get();
    Item result = new Item(match.getName(),
        match.getDescription(),
        match.getPicURL(),
        match.getBids(),
        match.getPurchasePrice(),
        match.getSellerName(),
        match.getBuyerName());
    if(match.getSold()){
      result.setBuyerName(match.getBuyerName());
      return result;
    }
    return result;
  }
}
