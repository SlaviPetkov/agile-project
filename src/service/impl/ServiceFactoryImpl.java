package service.impl;

import repository.*;
import service.*;

public class ServiceFactoryImpl implements ServiceFactory {

    @Override
    public UserService createUserService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Override
    public IngredientService createIngredientRepository(IngredientRepository ingredientRepository, UserRepository userRepository) {
        return new IngredientServiceImpl(ingredientRepository,userRepository);
    }

    @Override
    public ItemService createItemService(ItemRepository itemRepository, UserRepository userRepository) {
        return new ItemServiceImpl(itemRepository,userRepository);
    }

    @Override
    public TableService createTableService(TableRepository tableRepository,ReservationRepository reservationRepository,OrderRepository orderRepository,OrderService orderService) {
        return new TableServiceImpl(tableRepository, reservationRepository, orderRepository,orderService);
    }

    @Override
    public ReservationService createReservationService(ReservationRepository reservationRepository, TableRepository tableRepository) {
        return new ReservationServiceImpl(reservationRepository,tableRepository);
    }

    @Override
    public OrderItemService createOrderItemService(OrderItemRepository orderItemRepository, ItemRepository itemRepository, OrderRepository orderRepository) {
        return new OrderItemServiceImpl(orderItemRepository,itemRepository,orderRepository);
    }

    @Override
    public PaymentService createPaymentService(PaymentRepository paymentRepository) {
        return new PaymentServiceImpl(paymentRepository);
    }

    @Override
    public OrderService createOrderService(OrderRepository orderRepository, UserRepository userRepository,
                                           TableRepository tableRepository,PaymentRepository paymentRepository) {
        return new OrderServiceImpl(orderRepository,userRepository,tableRepository);
    }
}
