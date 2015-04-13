package dao;

import java.util.Set;

import beans.Student;

public interface StudentDao {
    /**
     * Creates a student into database
     * @param student
     * @throws DAOException
     */
    void create(Student student);

    /**
     * Searches one or more students into database
     * @param student
     * @return Set<Student>
     * @throws DAOException
     */
    Set<Student> search(Student student) throws DAOException;

    /**
     * Edits a student into database
     * @param student
     */
    void edit(Student student);

    /**
     * Edits a student password into database
     * @param student
     * @throws DAOException
     */
    void editPassword(Student student) throws DAOException;

    /**
     * Returns a student profil into database
     * @param student
     * @return Student
     * @throws DAOException
     */
    Student getProfile(Student student) throws DAOException;

    /**
     * Checks the existance of a student into database
     * @param student
     * @return int
     * @throws DAOException
     */
    int check(Student student) throws DAOException;

    /**
     * Deletes a student into database
     * @param student
     * @throws DAOException
     */
    void delete(Student student) throws DAOException;

    /**
     * Checks student login into database
     * @param student
     * @return Student
     * @throws DAOException
     */
    Student checkLogin(Student student) throws DAOException;

    /**
     * Returns all informations of a student into database (profile and gradebook)
     * @param student
     * @return Student
     * @throws DAOException
     */
    Student getAll(Student student) throws DAOException;
}