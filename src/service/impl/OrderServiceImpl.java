package service.impl;

import exceptions.NonExistingEntityException;
import model.entity.Order;
import model.entity.Table;
import repository.OrderRepository;
import repository.PaymentRepository;
import repository.TableRepository;
import repository.UserRepository;

import service.OrderService;

import java.util.ArrayList;
import java.util.List;

 class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TableRepository tableRepository;


    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            TableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;

    }

    @Override
    public List<Order> findAll(){
        List<Order> all = orderRepository.findAll();
        for (Order order:all ) {
            try {
                order.setUser(userRepository.findById(order.getUser().getId()));
                order.setTable(tableRepository.findById(order.getTable().getId()));

            } catch (NonExistingEntityException e) {
                e.printStackTrace();
            }


        }
        return all;
    }

    @Override
    public Order findById(Long id) {

        try {
            Order order = orderRepository.findById(id);
            order.setUser(userRepository.findById(order.getUser().getId()));
            order.setTable(tableRepository.findById(order.getTable().getId()));

            return order;
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertOrder(Order order) {
        return orderRepository.insert(order);
    }

    @Override
    public boolean updateOrder(Order order) {
        try {
            return orderRepository.update(order);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Order deleteById(Long id) {
        try {
            return orderRepository.deleteById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return  null;
    }

     @Override
     public List<Table> findAllTablesOrderByCurrentUser(Long id) {
         List<Long> allTableIds = orderRepository.findAllTablesOrderByCurrentUser(id);
         if(allTableIds == null){
             return null;
         }
         List<Table> tables = new ArrayList<>();
         try{
         for (Long tableId : allTableIds) {
             tables.add(tableRepository.findById(tableId));
         }}catch (NonExistingEntityException e) {
             e.printStackTrace();
         }
         return tables;
     }

     @Override
     public Order findOrderByUserAndTable(Long tableId) {

         Order order = orderRepository.findByUserAndTable(tableId);
         try {
             order.setUser(userRepository.findById(order.getUser().getId()));
             order.setTable(tableRepository.findById(order.getTable().getId()));

         } catch (NonExistingEntityException e) {
             e.printStackTrace();
         }


         return order;
     }

     @Override
     public List<Order> findAllActiveTablesByUser() {
         List<Order> activeOrders = orderRepository.findAllActiveTablesByUser();
         try {
         for (Order order : activeOrders) {
             order.setUser(userRepository.findById(order.getUser().getId()));
             order.setTable(tableRepository.findById(order.getTable().getId()));
         }
         } catch (NonExistingEntityException e) {
            e.printStackTrace();
         }
         return activeOrders;
     }
 }
