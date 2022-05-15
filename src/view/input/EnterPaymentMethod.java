package view.input;

import formating.Center;
import model.enums.PaymentTypeEnum;

import java.util.Scanner;

public class EnterPaymentMethod {
    private final Scanner scanner = new Scanner(System.in);
    public PaymentTypeEnum input() {
        System.out.println(Center.centerString(100,"ENTER PAYMENT METHOD"));
        System.out.printf("%-49s|%49s%n","1.CASH","2.TRANSACTION");
        String pMethod = scanner.nextLine();
        if(pMethod.equalsIgnoreCase("1")){
            return PaymentTypeEnum.CASH;
        }else if(pMethod.equalsIgnoreCase("2")){
            return PaymentTypeEnum.TRANSACTION;
        }else{
            System.out.println("Invalid action.Type 1 or 2");
            return input();
        }
    }
}
