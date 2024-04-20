package org.example.dao;

import org.example.models.Student;

import java.util.List;

public interface StudentDao {

    /**
     * Get a student from the datastore that has the given id.
     * If the id is not found, return null.
     *
     * @param id the id of the student to get from the datastore
     * @return a Student object
     */
    Student getStudentById(int id);

    /**
     * Gets all students from the datastore and returns them in a List
     *
     * @return all the students as Student objects in a List
     */

    List<Student> getAllStudents();

    /**
     * Find all students whose names contain the search strings. Returned students should
     * match last name search strings. If a search string is blank return all employees.
     * Be sure to use ILIKE for case-insensitive search matching!
     *
     * @param lastName the string to search for in the last_name, ignore if blank
     * @return all students whose name matches as Student objects in a List
     */

    List<Student> getStudentsByLastName(String lastName);

    /**
     * Find all students whose Teacher's name contain the search strings. Returned students
     * should match teacher last name search strings. If a search string is blank return all
     * students. Be sure to use ILIKE for case-insensitive search matching!
     *
     * @param teacherName the string to search for
     * @return all students whose name matches as Student objects in a List
     */

    List<Student> getStudentsByTeacherName(String teacherName);

    /**
     * Inserts a new student into the datastore.
     *
     * @param student the student object to insert
     * @return the student object with its new is filled in
     */
     Student createStudent(Student student);

    /**
     * Updates an existing student in the datastore.
     *
     * @param student the student object to update
     * @return the student object with its updated fields
     */
     Student updateStudent(Student student);

    /**
     * Removes a student from the datastore, which requires deleting
     * records from multiple tables.
     *
     * @param id the id of the student to remove
     * @return the number of students deleted
     */
     int deleteStudentById(int id);

}

