package repository;



import model.entity.Reservation;
import model.entity.Table;
import model.entity.User;

import java.util.List;

public interface TableRepository extends Repository<Long, Table> {


    List<Table> findAllFreeTables();

    Table findByCode(String tableCode);

    boolean updateTableToReserved(Table table);

    boolean updateTableToActive(Table table);

    List<Table> findAllActiveTablesByUser();

    void updateToFree(Table table);
}
