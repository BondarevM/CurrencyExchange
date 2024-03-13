package by.mikhail.currencyexchange.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {
    List<T> findAll() throws SQLException;
    Optional<T> findByCode(K code) throws SQLException;
    void update (T entity);

}
