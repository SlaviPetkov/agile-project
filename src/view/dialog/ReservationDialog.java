package view.dialog;

import model.entity.Reservation;
import model.entity.Table;
import model.enums.ReservationStatusEnum;
import model.enums.TableStatusEnum;
import security.CurrentUser;
import service.ReservationService;
import service.TableService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationDialog implements EntityDialog<Reservation> {
    public static final Pattern SPECIAL_CHARACTERS_PATTERN  = Pattern.compile("\\w+");
    public static final  Pattern MOBILE_PATTERN =
            Pattern.compile("08\\d{8}");
    public static final DateTimeFormatter DATE_TIME_FORMATTER =DateTimeFormatter.ofPattern("dd-M-yyyy H:mm");
    public static final Pattern DATE_TIME_STRING_PATTERN = Pattern.compile("^\\d{2}-\\d{1}-\\d{4}\\s\\d{2}:\\d{2}$");
    private final Scanner scanner = new Scanner(System.in);
    public  ReservationDialog() {


    }

    @Override
    public Reservation input() {
        Reservation reservation = new Reservation();

        reservation.setUser(CurrentUser.getUser());

    while(reservation.getFullName()== null){
        System.out.println("Enter full name for reservation:");
        String fullName = scanner.nextLine().trim();
        Matcher matcher = SPECIAL_CHARACTERS_PATTERN.matcher(fullName);
        if( fullName.length() <2 || fullName.length() > 25 || !matcher.matches() ){
            System.out.println("Invalid name.Name must be without special characters and between 2 and 25 characters long");
        }else{
            reservation.setFullName(fullName);
        }
    }
        while(reservation.getMobile()== null){
            System.out.println("Enter mobile number:");
            String mobile = scanner.nextLine().trim();
            Matcher matcher = MOBILE_PATTERN.matcher(mobile);
            if( !matcher.matches() ){
                System.out.println("Invalid mobile.Mobile number must be 10 digits, starting with \"08\"");
            }else{
                reservation.setMobile(mobile);
            }
        }
        while(reservation.getDateTimeOfReservation()== null){
            System.out.println("Enter reservation date and time of pattern : dd-M-yyyy hh:mm");
            String dateTime = scanner.nextLine().trim();
            Matcher matcher = DATE_TIME_STRING_PATTERN.matcher(dateTime);
            if(!matcher.matches()){
                System.out.println("Invalid date and time .Please enter date and time from the future with  pattern : dd-M-yyyy hh:mm");
                continue;
            }
            LocalDateTime reservationDate =  LocalDateTime.parse(dateTime,DATE_TIME_FORMATTER);
            if( reservationDate.isBefore(LocalDateTime.now()) ||
                        reservationDate.isAfter(LocalDateTime.now().plusDays(1))){
                System.out.println("Invalid date and time .Please enter date and time from the future with  pattern : dd-M-yyyy hh:mm");
            }else{
                reservation.setDateTimeOfReservation(reservationDate);
            }
        }

        reservation.setStatus(ReservationStatusEnum.NEW);



        return reservation;
    }
}
