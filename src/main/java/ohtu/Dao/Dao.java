package ohtu.Dao;

import java.sql.*;
import java.util.*;
import ohtu.domain.Book;

public interface Dao<T, K> {

    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;

    T save(T object) throws SQLException;
    
    T update(T object) throws SQLException;

    void delete(K key) throws SQLException;
    
    public List<T> findread()throws SQLException;
    
    public List<T> findunread()throws SQLException;
}


