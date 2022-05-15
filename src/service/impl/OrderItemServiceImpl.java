package service.impl;

import exceptions.NonExistingEntityException;
import model.entity.OrderItem;
import repository.ItemRepository;
import repository.OrderItemRepository;
import repository.OrderRepository;

import service.OrderItemService;

import java.util.List;

 class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository ;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ItemRepository itemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;

        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderItem> findAll() {
        try {
            List<OrderItem> all = orderItemRepository.findAll();
            for (OrderItem orderItem : all) {
                orderItem.setItem(itemRepository.findById(orderItem.getItem().getId()));
                orderItem.setOrder(orderRepository.findById(orderItem.getOrder().getId()));
            }
            return all;
        } catch ( NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderItem findById(Long id) {
        try {
            OrderItem orderItem = orderItemRepository.findById(id);
            orderItem.setItem(itemRepository.findById(orderItem.getItem().getId()));
            orderItem.setOrder(orderRepository.findById(orderItem.getOrder().getId()));
            return orderItem;
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertOrderItem(OrderItem orderItem) {
        return orderItemRepository.insert(orderItem);
    }

    @Override
    public boolean updateOrderItem(OrderItem orderItem) {
        try {
            return orderItemRepository.update(orderItem);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public OrderItem deleteById(Long id) {
        try {
            return orderItemRepository.deleteById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

     @Override
     public List<OrderItem> findAllByOrderId(Long id) {
         List<OrderItem> found = orderItemRepository.findByOrderId(id);
         try {
         for (OrderItem orderItem : found) {
             orderItem.setItem(itemRepository.findById(orderItem.getItem().getId()));

         }
         } catch (NonExistingEntityException e) {
             e.printStackTrace();
         }
         return found;
     }
 }
