package service;

import model.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll() ;
    Item findById(Long id);
    boolean insertItem(Item item);
    boolean updateItem(Item item);

    Item  deleteById(Long id);

    List<Long> findAllItemIds();
}
