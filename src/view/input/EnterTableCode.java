package view.input;

import model.entity.Table;
import service.TableService;

import java.util.Scanner;

public class EnterTableCode {
    private final TableService tableService;
    private final Scanner scanner =new Scanner(System.in);

    public EnterTableCode(TableService tableService) {
        this.tableService = tableService;
    }
    public Table input(){
        String tableCode = scanner.nextLine();
        if(tableService.findAll().stream().map(Table::getCode).noneMatch(c->c.equals(tableCode))){
            System.out.println("Enter valid table code");
            return input();
        }
        return   tableService.findTableByCode(tableCode);
    }
}
