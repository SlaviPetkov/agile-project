package view.dialog;

import formating.Center;
import model.entity.Table;
import model.enums.TableStatusEnum;
import security.CurrentUser;
import service.TableService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static model.enums.TableStatusEnum.*;

public class TableDialog  {



    public static Table input(TableService tableService) {
        Scanner scanner =new Scanner(System.in);
        List<Table> freeTables = tableService.findAllFreeTables();
        System.out.println("Enter table code:");
        String code = scanner.nextLine().trim();
        Optional<Table> found = freeTables.stream().filter(table -> table.getCode().equals(code)).findAny();
        if(found.isEmpty()){
            System.out.println("Invalid table code.Enter valid table code:");
            return TableDialog.input(tableService);
        }
        return found.get();
    }
    public static void showFreeTables(List<Table> freeTables){
        System.out.println("Free tables:");
        System.out.println("-".repeat(50));
        for (Table table : freeTables) {
            System.out.printf("%-24s|%24s%n","Table code: " + table.getCode(),"Table capacity: "+ table.getCapacity());
            System.out.println("-".repeat(50));
        }
    }
    public  static void showAllTables(TableService tableService){
        List<Table> allTables = tableService.findAll();
        for (TableStatusEnum status : values()) {
            switch (status){
                case FREE:
                    System.out.println(Center.centerString(100,status.toString()+" tables"));
                    System.out.println("-".repeat(100));
                    allTables.stream().filter(t->t.getStatus().equals(status)).forEach(t-> {
                        System.out.printf("|%7s%-15s|%10s%-23s|",
                                "",
                                "table #" +t.getCode() ,
                                "",
                                "capacity = "+t.getCapacity());
                        System.out.println();
                    });
                    System.out.println("-".repeat(100));
                    break;
                case RESERVED:
                    System.out.println(Center.centerString(100,status.toString()+" tables"));
                    System.out.println("-".repeat(100));
                    allTables.stream()
                            .filter(t->t.getStatus().equals(RESERVED))
                            .filter(t->t.getReservation().getUser().equals(CurrentUser.getUser()))
                            .forEach(t->{
                                System.out.printf("|%7s%-15s|%10s%-23s|%s|",
                                        "",
                                        "table #" +t.getCode() ,
                                        "",
                                        "capacity = "+t.getCapacity(),
                                        "reservation time =  "+t.getReservation()
                                                .getDateTimeOfReservation()
                                                .format(DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm")));
                                System.out.println();

                            });

                    System.out.println("-".repeat(100));
                    break;
                case ACTIVE:
                    System.out.println(Center.centerString(100,status.toString()+" tables"));
                    System.out.println("-".repeat(100));
                    allTables.stream().filter(t->t.getStatus().equals(ACTIVE))
                            .filter(t->t.getOrder().getUser().equals(CurrentUser.getUser()))
                            .forEach(t->{
                                System.out.printf("|%s||%s|",
                                        Center.centerString(48,"table #" +t.getCode()) ,

                                        Center.centerString(48, ChronoUnit.MINUTES.between( t.getOrder().getModified(),LocalDateTime.now())+" minutes ago"));

                                System.out.println();

                            });

                    System.out.println("-".repeat(100));
                    break;
            }

        }
    }
}
