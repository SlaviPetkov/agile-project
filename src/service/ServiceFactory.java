package service;

import repository.*;

public interface ServiceFactory {

    UserService createUserService(UserRepository userRepository);
    IngredientService createIngredientRepository(IngredientRepository ingredientRepository,
                                                    UserRepository userRepository );
    ItemService createItemService(ItemRepository itemRepository,UserRepository userRepository);
    TableService createTableService(TableRepository tableRepository,ReservationRepository reservationRepository,OrderRepository orderRepository,OrderService orderService);
    ReservationService createReservationService(ReservationRepository reservationRepository,
                                                TableRepository tableRepository);
    OrderItemService createOrderItemService(OrderItemRepository orderItemRepository,
                                            ItemRepository itemRepository,
                                            OrderRepository orderRepository);
    PaymentService createPaymentService(PaymentRepository paymentRepository);
    OrderService createOrderService(OrderRepository orderRepository,
                                    UserRepository userRepository,
                                    TableRepository tableRepository,
                                    PaymentRepository paymentRepository);
}
