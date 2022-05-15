package service;

import model.entity.Order;
import model.entity.Table;

import java.util.List;

public interface OrderService {
    List<Order> findAll() ;
    Order findById(Long id);
    boolean insertOrder(Order order);
    boolean updateOrder(Order order);

    Order  deleteById(Long id);

    List<Table> findAllTablesOrderByCurrentUser(Long id);

    Order findOrderByUserAndTable(Long tableId);

    List<Order> findAllActiveTablesByUser();
}
