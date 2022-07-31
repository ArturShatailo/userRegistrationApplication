package registration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Crudable<T> {

    int save(T t);

    int update(T t);

    int delete(int id);

    T getById(int id);

    T getByEmail(String email);

    List<T> getAll();

    T createObjectByValue(ResultSet R, T T) throws SQLException;

}
