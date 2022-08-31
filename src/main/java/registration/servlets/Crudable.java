package registration.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Crudable<T> {

    /**
     * Using request to the database for adding, creates new database record with data specified as an object fields.
     * The needed to be saved object is received in @param 't'.
     *
     * @param t Object that should be saved as a database record
     * @return integer value as a status of database change
     */
    int save(T t);

    /**
     * Using request to the database for updating, updates existing database record and replace its data with
     * data specified as an object fields. The object is received in @param 't'.
     *
     * @param t Object that should be updated
     * @return integer value as a status of database change
     */
    int update(T t);

    /**
     * Using request to the database for deleting, exclude existing database record from table finding the needed record
     * according to the received in @param 'id' value of id field (column).
     *
     * @param id column 'id' value as a field 'id' of object that should be deleted from database table
     * @return integer value as a status of database change
     */
    int delete(int id);

    /**
     * Using request to the database for searching, finds existing database table record with 'id' column equal to
     * received in @param 'id' value.
     *
     * @param id column 'id' value as a field 'id' of object that should be found in database table
     * @return created object with data in fields from database table selected from a found row according
     * to 'id' column value
     */
    T getById(int id);

    /**
     * Creates List collection and fills it out with all objects created according to all rows of database table.
     *
     * @return created List of all objects received from database table
     */
    List<T> getAll();

    /**
     * Fills fields of received object with data that is received as a columns of database table row.
     *
     * @param R ResultSet object from database that includes one row of database table
     * @param T Object that should be filled out with data
     * @return Object with set fields
     * @throws SQLException can be thrown in case of none data in the ResultSet object or absence of ResultSet object.
     */
    T createObjectByValue(ResultSet R, T T) throws SQLException;

}
