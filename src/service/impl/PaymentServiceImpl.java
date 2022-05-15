package service.impl;

import exceptions.NonExistingEntityException;
import model.entity.Payment;
import repository.PaymentRepository;
import service.PaymentService;

import java.sql.SQLException;
import java.util.List;

class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findAll() {

        return paymentRepository.findAll();


    }
    @Override
    public Payment findById(Long id) {
        try {
            return paymentRepository.findById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertPayment(Payment payment) {
        return paymentRepository.insert(payment);
    }

    @Override
    public boolean updatePayment(Payment payment) {
        try {
            return paymentRepository.update(payment);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Payment deleteById(Long id) {
        try {
            return paymentRepository.deleteById(id);
        } catch (NonExistingEntityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void printRecipe(Payment payment) {
        paymentRepository.printRecipe(payment);
    }
}
