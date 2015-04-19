package dao;

import java.util.Set;

import beans.Teacher;

public interface TeacherDao {
    /**
     * Creates a teacher into database
     * @param teacher
     */
    void create(Teacher teacher);

    /**
     * Searches one or more teachers into database
     * @param teacher
     * @return Set<Teacher>
     * @throws DAOException
     */
    Set<Teacher> search(Teacher teacher) throws DAOException;

    /**
     * Edits a teacher into database
     * @param teacher
     * @throws DAOException
     */
    void edit(Teacher teacher) throws DAOException;

    /**
     * Checks the existance of a teacher into database
     * @param teacher
     * @return int
     * @throws DAOException
     */
    int check(Teacher teacher) throws DAOException;

    /**
     * Returns a teacher into database
     * @param teacher
     * @return Teacher
     * @throws DAOException
     */
    Teacher get(Teacher teacher) throws DAOException;

    /**
     * Deletes a teacher into database
     * @param teacher
     * @throws DAOException
     */
    void delete(Teacher teacher) throws DAOException;

    /**
     * Checks teacher login into database
     * @param teacher
     * @return Teacher
     * @throws DAOException
     */
    Teacher checkLogin(Teacher teacher) throws DAOException;
}
