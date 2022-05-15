package repository;



import exceptions.NonExistingEntityException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Repository<K,V extends Identifiable<K>> {
    V findById(K id) throws NonExistingEntityException;
    boolean insert(V entity);
    boolean update(V entity) throws NonExistingEntityException;
    V deleteById(K id) throws NonExistingEntityException;
    List<V> findAll() ;


    long count(String table) throws SQLException;
}
