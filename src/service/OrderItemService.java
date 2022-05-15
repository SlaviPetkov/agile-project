package service;

import model.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAll() ;
    OrderItem findById(Long id);
    boolean insertOrderItem(OrderItem orderItem);
    boolean updateOrderItem(OrderItem orderItem);

    OrderItem  deleteById(Long id);

    List<OrderItem> findAllByOrderId(Long id);
}
