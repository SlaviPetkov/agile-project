package repository.impl;

import repository.*;

import java.sql.Connection;

public class RepositoryFactoryImpl implements RepositoryFactory {



    @Override
    public UserRepository createUserRepository(Connection connection) {
        return new UserRepositoryImpl(connection);
    }

    @Override
    public IngredientRepository createIngredientRepository(Connection connection) {
        return new IngredientRepositoryImpl(connection);
    }

    @Override
    public ItemRepository createItemRepository(Connection connection) {
        return new ItemRepositoryImpl(connection);
    }

    @Override
    public TableRepository createTableRepository(Connection connection) {
        return new TableRepositoryImpl(connection);
    }

    @Override
    public ReservationRepository createReservationRepository(Connection connection) {
        return new ReservationRepositoryImpl(connection);
    }

    @Override
    public OrderItemRepository createOrderItemRepository(Connection connection) {
        return new OrderItemRepositoryImpl(connection);
    }

    @Override
    public OrderRepository createOrderRepository(Connection connection) {
        return new OrderRepositoryImpl(connection);
    }

    @Override
    public PaymentRepository createPaymentRepository(Connection connection, UserRepository userRepository, TableRepository tableRepository, OrderRepository orderRepository) {
        return new PaymentRepositoryImpl(connection,userRepository,tableRepository,orderRepository);
    }
}
