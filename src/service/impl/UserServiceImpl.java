package service.impl;

import exceptions.ConstraintViolationException;
import exceptions.InvalidEntityException;
import exceptions.NonExistingEntityException;
import model.entity.User;
import repository.UserRepository;

import service.UserService;
import util.UserValidator;
import util.Validator;
import view.dialog.RegisterDialog;

import java.util.List;
 class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    private final String TABLE_NAME = "users";
    private final Validator validator = new UserValidator();

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;

    }

    @Override
    public List<User> findAll() {
       return userRepo.findAll();



    }

    @Override
    public User findById(Long id) {

        try {
            return userRepo.findById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertUser(User user)  {

        return userRepo.insert(user);
    }

    @Override
    public boolean updateUser(User user) {
        try {
            return userRepo.update(user);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User deleteById(Long id) {
        try {
            return userRepo.deleteById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

     @Override
     public User findByUsernameAndPassword(String username, String password) {
             return userRepo.findByUsernameAndPassword(username,password);

     }


 }
