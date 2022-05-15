import command.impl.*;
import controller.*;
import exceptions.InvalidEntityException;
import model.entity.*;
import repository.*;
import repository.impl.*;
import service.*;
import service.impl.*;
import view.Menu.Menu;
import view.Menu.Option;

import java.sql.*;
import java.util.Collections;
import java.util.List;

import static model.enums.RoleTypeEnum.*;

public class Main {
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String DB_URL = "jdbc:mysql://localhost:3306/restaurant_db";
    public static String DB_USERNAME ="root";
    public static String DB_PASSWORD= "1234";
    public static void main(String[] args) throws InvalidEntityException {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection =null;
        try {
          connection   = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RepositoryFactory repoFac = new RepositoryFactoryImpl();

        UserRepository userRepository = repoFac.createUserRepository(connection);
        IngredientRepository ingredientRepository =  repoFac.createIngredientRepository(connection);
        ItemRepository itemRepository = repoFac.createItemRepository(connection);
        TableRepository tableRepository = repoFac.createTableRepository(connection);
        ReservationRepository reservationRepository = repoFac.createReservationRepository(connection);
        OrderItemRepository orderItemRepository = repoFac.createOrderItemRepository(connection);
        OrderRepository orderRepository = repoFac.createOrderRepository(connection);
        PaymentRepository paymentRepository = repoFac.createPaymentRepository(connection,userRepository,tableRepository,orderRepository);

        ServiceFactory serviceFac =  new ServiceFactoryImpl();

        UserService userService = serviceFac.createUserService(userRepository);
        IngredientService ingredientService =serviceFac.createIngredientRepository(ingredientRepository,userRepository);
        ItemService itemService = serviceFac.createItemService(itemRepository,userRepository);

        ReservationService reservationService = serviceFac.createReservationService(reservationRepository,tableRepository);
        OrderItemService orderItemService = serviceFac.createOrderItemService(orderItemRepository,itemRepository,orderRepository);
        PaymentService paymentService = serviceFac.createPaymentService(paymentRepository);
        OrderService orderService = serviceFac.createOrderService(orderRepository,userRepository,tableRepository,paymentRepository);
        TableService tableService = serviceFac.createTableService(tableRepository,reservationRepository,orderRepository,orderService);
        UserController userController = new UserController(userService, ingredientService, itemService, tableService, reservationService, orderService);
        IngredientController ingredientController =new IngredientController(ingredientService);
        ItemController itemController= new ItemController(itemService);
        ReservationController reservationController = new ReservationController(reservationService,tableService);
        OrderController orderController = new OrderController(tableService,orderService,reservationService, itemService, itemController,orderItemService, paymentService);
itemService.findAll().stream().forEach(System.out::println);

        new Menu("Main menu", List.of(new Option("Login",
                        Collections.emptyList(),new LoginCommand(userController))
                ,new Option("Register User",
                        List.of(ADMINISTRATOR,MANAGER),new RegisterUserCommand(userController)),
                new Option("Show Inventory",
                        List.of(ADMINISTRATOR,MANAGER),new ShowInventoryCommand(ingredientController)),
                new Option("Add Ingredient",
                        List.of(ADMINISTRATOR,MANAGER),new AddIngredientCommand(ingredientController)),
                new Option("Show Items",
                        List.of(ADMINISTRATOR,MANAGER),new ShowItemsCommand(itemController)),
                new Option("Add Item",
                        List.of(ADMINISTRATOR,MANAGER),new AddItemCommand(itemController)),
                new Option("Make reservation",
                        List.of(ADMINISTRATOR,MANAGER,SERVER),new MakeReservation(reservationController)),
                new Option("Show reservations",
                        List.of(ADMINISTRATOR,MANAGER,SERVER),new ShowReservationCommand(reservationController)),
                new Option("Make order",
                        List.of(ADMINISTRATOR,MANAGER,SERVER),new MakeOrderCommand(orderController)))).show();
    }

    }

