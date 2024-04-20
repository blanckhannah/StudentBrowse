package org.example.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.exception.DaoException;
import org.example.models.Student;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcStudentDAO implements StudentDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcStudentDAO(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Student student = mapRowToStudent(results);
                students.add(student);
            }
        return students;
    }
    public Student getStudentById(int id) {
        Student student = null;
        String sql = "SELECT * FROM student WHERE student_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                student = mapRowToStudent(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return student;
    }
    public List<Student> getStudentsByLastName(String lastName) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student WHERE last_name ILIKE ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + lastName + "%");
            while (results.next()) {
                Student studentResult = mapRowToStudent(results);
                students.add(studentResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return students;
    }
    public List<Student> getStudentsByTeacherName(String teacherName) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student JOIN student_teacher ON student_teacher.student_id = student.student_id " +
                "JOIN teacher ON teacher.teacher_id = student_teacher.teacher_id " +
                "WHERE teacher.last_name ILIKE ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + teacherName + "%");
            while (results.next()) {
                Student studentResult = mapRowToStudent(results);
                students.add(studentResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return students;
    }
    public Student createStudent(Student student) {
        Student newStudent = null;

        String sql = "INSERT INTO student (first_name, last_name, address, grade) " +
                "VALUES (?, ?, ?, ?) RETURNING student_id;";
        try {
            int newStudentId = jdbcTemplate.queryForObject(sql, int.class, student.getFirst_name(), student.getLast_name(),
                    student.getAddress(), student.getGrade());
            newStudent = getStudentById(newStudentId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newStudent;
    }
    public Student updateStudent(Student student) {
        Student updatedStudent = null;

        String sql = "UPDATE student SET first_name = ?, last_name = ?, address = ?, grade = ? " +
                "WHERE student_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, student.getFirst_name(), student.getLast_name(), student.getAddress(), student.getGrade(), student.getId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one.");
            } else {
                updatedStudent = getStudentById(student.getId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedStudent;
    }
    public int deleteStudentById(int id) {
        int numberOfRows = 0;
        String deleteAttendanceSql = "DELETE FROM attendance WHERE student_id = ?;";
        String deleteDisciplineSql = "DELETE FROM discipline WHERE student_id = ?;";
        String deleteStudentTeacherSql = "DELETE FROM student_teacher WHERE student_id = ?;";
        String deleteStudentParentSql = "DELETE FROM student_parent WHERE student_id = ?;";
        String deleteStudentSql = "DELETE FROM student WHERE student_id = ?;";

        try {
            jdbcTemplate.update(deleteAttendanceSql, id);
            jdbcTemplate.update(deleteDisciplineSql, id);
            jdbcTemplate.update(deleteStudentTeacherSql, id);
            jdbcTemplate.update(deleteStudentParentSql, id);
            numberOfRows = jdbcTemplate.update(deleteStudentSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    public Student mapRowToStudent (SqlRowSet result) {
        Student student = new Student();
        student.setId(result.getInt("student_id"));
        student.setFirst_name(result.getString("first_name"));
        student.setLast_name(result.getString("last_name"));
        student.setAddress(result.getString("address"));
        student.setGrade(result.getInt("grade"));
        return student;
    }
}
