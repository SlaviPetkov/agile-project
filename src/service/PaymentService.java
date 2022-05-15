package service;

import model.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> findAll() ;
    Payment findById(Long id);
    boolean insertPayment(Payment payment);
    boolean updatePayment(Payment payment);

    Payment  deleteById(Long id);
    void printRecipe(Payment payment);
}
