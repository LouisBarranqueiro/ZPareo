package dao;

import java.util.Set;
import beans.Subject;

public interface SubjectDao {
    /**
     * Creates a subject into database
     * @param subject
     * @throws DAOException
     */
    void create(Subject subject) throws DAOException;

    /**
     * Searches one or more matters into database
     * @param subject
     * @throws DAOException
     */
    Set<Subject> search(Subject subject) throws DAOException;

    /**
     * Edits a subject into database
     * @param subject
     * @throws DAOException
     */
    void edit(Subject subject) throws DAOException;

    /**
     * Checks the existance of a subject into database
     * @param subject
     * @throws DAOException
     */
    int check(Subject subject) throws DAOException;

    /**
     * Returns a subject into database
     * @param subject
     * @throws DAOException
     */
    Subject get(Subject subject) throws DAOException;

    /**
     * Deletes a subject into database
     * @param subject
     * @throws DAOException
     */
    void delete(Subject subject) throws DAOException;
}
