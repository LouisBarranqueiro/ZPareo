package dao;

import java.util.Set;

import beans.Administrator;

public interface AdministratorDao {
    /**
     * Creates an administrator into database
     * @param administrator
     * @throws DAOException
     */
    void create(Administrator administrator) throws DAOException;

    /**
     * Searches one or more administrators into database
     * @param administrator
     * @throws DAOException
     */
    Set<Administrator> search(Administrator administrator) throws DAOException;

    /**
     * Edits an administrator into database
     * @param administrator
     */
    void edit(Administrator administrator);

    /**
     * Returns an administrator into database
     * @param administrator
     * @throws DAOException
     */
    Administrator get(Administrator administrator) throws DAOException;

    /**
     * Checks the existence of an administrator into database
     * @param administrator
     * @throws DAOException
     */
    int check(Administrator administrator) throws DAOException;

    /**
     * Deletes an administrator into database
     * @param administrator
     * @throws DAOException
     */
    void delete(Administrator administrator) throws DAOException;

    /**
     * Checks administrator login into database
     * @param administrator
     * @throws DAOException
     */
    Administrator checkLogin(Administrator administrator) throws DAOException;

}
