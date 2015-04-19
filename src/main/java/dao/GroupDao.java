package dao;

import java.util.Set;

import beans.Group;

public interface GroupDao {
    /**
     * Creates a group into database
     * @param group
     * @throws DAOException
     */
    void create(Group group) throws DAOException;

    /**
     * Searches one or more groups into database
     * @param group
     * @return Set<Group>
     * @throws DAOException
     */
    Set<Group> search(Group group) throws DAOException;

    /**
     * Edits a group into database
     * @param group
     * @throws DAOException
     */
    void edit(Group group) throws DAOException;

    /**
     * Checks the existance of a group into database
     * @param group
     * @return int
     * @throws DAOException
     */
    int check(Group group) throws DAOException;

    /**
     * Returns a group into database
     * @param group
     * @return Group
     * @throws DAOException
     */
    Group get(Group group) throws DAOException;

    /**
     * Deletes a group into database
     * @param group
     * @throws DAOException
     */
    void delete(Group group) throws DAOException;
}
