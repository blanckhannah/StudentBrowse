package org.example.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.exception.DaoException;
import org.example.models.Teacher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTeacherDAO {
    private JdbcTemplate jdbcTemplate;
    public JdbcTeacherDAO(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();

        String sql = "SELECT * FROM Teacher";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            Teacher teacher = mapRowToTeacher(results);
            teachers.add(teacher);
        }
        return teachers;
    }
    public Teacher getTeacherById(int id) {
        Teacher teacher = null;
        String sql = "SELECT * FROM teacher WHERE teacher_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()) {
                teacher = mapRowToTeacher(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return teacher;
    }
    public List<Teacher> getTeacherByLastName(String lastName) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher WHERE last_name ILIKE ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + lastName + "%");
            while(results.next()) {
                Teacher teacherResult = mapRowToTeacher(results);
                teachers.add(teacherResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return teachers;
    }
    public Teacher createTeacher(Teacher teacher) {
        Teacher newTeacher = null;

        String sql = "INSERT INTO teacher (first_name, last_name, email) " +
                "VALUES (?, ?, ?) RETURNING teacher_id;";
        try {
            int newTeacherId = jdbcTemplate.queryForObject(sql, int.class, teacher.getFirst_name(),
                    teacher.getLast_name(), teacher.getEmail());
            newTeacher = getTeacherById(newTeacherId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTeacher;
    }
    public Teacher updateTeacher(Teacher teacher) {
        Teacher updatedTeacher = null;

        String sql = "UPDATE teacher SET first_name = ?, last_name = ?, email = ? " +
                "WHERE teacher_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, teacher.getFirst_name(), teacher.getLast_name(), teacher.getEmail(), teacher.getId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedTeacher = getTeacherById((teacher.getId()));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedTeacher;
    }
    public int deleteTeacherById(int id) {
        int numberOfRows = 0;
        String deleteStudentTeacherSql = "DELETE FROM student_teacher WHERE teacher_id = ?;";
        String deleteTeacherSql = "DELETE FROM teacher WHERE teacher_id = ?;";
        try {
            jdbcTemplate.update(deleteStudentTeacherSql, id);
            numberOfRows = jdbcTemplate.update(deleteTeacherSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    public Teacher mapRowToTeacher (SqlRowSet result) {
        Teacher teacher = new Teacher();
        teacher.setId(result.getInt("teacher_id"));
        teacher.setFirst_name(result.getString("first_name"));
        teacher.setLast_name(result.getString("last_name"));
        teacher.setEmail(result.getString("email"));
        return teacher;
    }
}
