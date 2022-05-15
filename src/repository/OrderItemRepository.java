package repository;



import model.entity.OrderItem;
import model.entity.Table;

import java.util.List;

public interface OrderItemRepository extends Repository<Long, OrderItem> {


    List<OrderItem> findByOrderId(Long id);
}
