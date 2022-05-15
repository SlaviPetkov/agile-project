package repository;



import model.entity.Order;
import model.entity.Table;

import java.util.List;

public interface OrderRepository extends Repository<Long, Order> {


    List<Long> findAllTablesOrderByCurrentUser(Long id);

    Order findByUserAndTable(Long tableId);

    List<Order> findAllActiveTablesByUser();
}
