package dao;

import java.util.Set;

import beans.Test;

public interface TestDao {
    /**
     * Creates a test into database
     * @param test
     * @throws DAOException
     */
    void create(Test test) throws DAOException;

    /**
     * Searches one or more tests into database
     * @param test
     * @return Set<Test>
     * @throws DAOException
     */
    Set<Test> search(Test test) throws DAOException;

    /**
     * Edits a test into database
     * @param test
     * @throws DAOException
     */
    void edit(Test test) throws DAOException;

    /**
     * Returns a test into database
     * @param test
     * @return Test
     * @throws DAOException
     */
    Test get(Test test) throws DAOException;

    /**
     * Deletes a test into database
     * @param test
     * @throws DAOException
     */
    void delete(Test test) throws DAOException;
}
