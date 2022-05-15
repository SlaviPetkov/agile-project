package repository.impl;

import repository.*;

import java.sql.Connection;

public interface RepositoryFactory {

    UserRepository createUserRepository(Connection connection);
    IngredientRepository createIngredientRepository(Connection connection);
    ItemRepository createItemRepository(Connection connection);
    TableRepository createTableRepository(Connection connection);
    ReservationRepository createReservationRepository(Connection connection);
    OrderItemRepository createOrderItemRepository(Connection connection);
    OrderRepository createOrderRepository(Connection connection);
    PaymentRepository createPaymentRepository(Connection connection, UserRepository userRepository,
                                                  TableRepository tableRepository, OrderRepository orderRepository);


}
