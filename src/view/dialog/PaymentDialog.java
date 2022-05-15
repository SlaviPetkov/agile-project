package view.dialog;

import model.entity.Payment;
import model.entity.Table;
import model.enums.PaymentTypeEnum;
import security.CurrentUser;

public class PaymentDialog implements EntityDialog<Payment> {
    private final Table table ;
    private final PaymentTypeEnum paymentType;

    public PaymentDialog(Table table, PaymentTypeEnum paymentType) {
        this.table = table;
        this.paymentType = paymentType;
    }

    @Override
    public Payment input() {
        Payment payment = new Payment();
        payment.setUser(CurrentUser.getUser());
        payment.setTable(table);
        payment.setOrder(table.getOrder());
        payment.setPrice(table.getOrder().getPrice());
        payment.setType(paymentType);

        return payment;
    }
}
