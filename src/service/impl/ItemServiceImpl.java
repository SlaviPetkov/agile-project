package service.impl;

import exceptions.NonExistingEntityException;
import model.entity.Item;
import repository.ItemRepository;
import repository.UserRepository;

import service.ItemService;

import java.util.List;

 class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Item> findAll() {
        List<Item> all = itemRepository.findAll();
        for (Item item : all) {
            try {
                item.setUser(userRepository.findById(item.getUser().getId()));
            } catch (NonExistingEntityException e) {
                e.printStackTrace();
            }
        }
        return all;
    }

    @Override
    public Item findById(Long id) {
        try {
            Item item = itemRepository.findById(id);
            item.setUser(userRepository.findById(item.getUser().getId()));
            return item;
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertItem(Item item) {
        return itemRepository.insert(item);
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            return itemRepository.update(item);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Item deleteById(Long id) {
        try {
            return itemRepository.deleteById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

     @Override
     public List<Long> findAllItemIds() {
         return itemRepository.findAllItemIds();
     }
 }
