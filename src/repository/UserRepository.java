package repository;



import exceptions.InvalidEntityException;
import model.entity.User;

public interface UserRepository extends Repository<Long, User> {


    User findByUsernameAndPassword(String username, String password) ;
}
