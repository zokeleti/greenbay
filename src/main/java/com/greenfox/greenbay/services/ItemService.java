package com.greenfox.greenbay.services;

import com.greenfox.greenbay.models.Item;
import com.greenfox.greenbay.models.exceptions.InvalidPriceException;
import com.greenfox.greenbay.models.exceptions.InvalidURLException;
import com.greenfox.greenbay.models.exceptions.MissingFieldException;
import com.greenfox.greenbay.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
}
