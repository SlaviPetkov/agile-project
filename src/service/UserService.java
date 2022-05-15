package service;

import exceptions.InvalidEntityException;
import model.entity.User;


import java.sql.SQLException;
import java.util.List;

public interface UserService {


    List<User> findAll() ;
    User findById(Long id);
    boolean insertUser(User user) ;
    boolean updateUser(User user);

    User  deleteById(Long id);

    User findByUsernameAndPassword(String username, String password);


}
