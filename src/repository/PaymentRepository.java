package repository;



import model.entity.Payment;
import model.entity.Table;

public interface PaymentRepository extends Repository<Long, Payment> {


    void printRecipe(Payment payment);
}
