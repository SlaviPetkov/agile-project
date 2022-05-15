package repository;



import model.entity.Item;
import model.entity.User;

import java.util.List;

public interface ItemRepository extends Repository<Long, Item> {


    List<Long> findAllItemIds();
}
