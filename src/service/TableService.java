package service;

import model.entity.Reservation;
import model.entity.Table;


import java.util.List;

public interface TableService {
    List<Table> findAll() ;
    Table findById(Long id);
    boolean insertTable(Table table);
    boolean updateTable(Table table);

    Table  deleteById(Long id);

    List<Table> findAllFreeTables();

    Table findTableByCode(String tableCode);

    boolean updateTableToReserved(Table table);

    boolean updateTableToActive(Table table);


    void updateTableToFree(Table table);
}
