package org.example.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.exception.DaoException;
import org.example.models.Parent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcParentDAO {
    private JdbcTemplate jdbcTemplate;
    public JdbcParentDAO(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Parent> getAllParents() {
        List <Parent> parents = new ArrayList<>();

        String sql = "SELECT * FROM Parent";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Parent parent = mapRowToParent(results);
                parents.add(parent);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return parents;
    }
    public Parent getParentById(int id) {
        Parent parent = null;
        String sql = "SELECT * FROM parent WHERE parent_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                parent = mapRowToParent(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return parent;
    }
    public List<Parent> getParentsByLastName(String lastName) {
        List<Parent> parents = new ArrayList<>();
        String sql = "SELECT * FROM parent WHERE last_name ILIKE ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + lastName + "%");
            while (results.next()) {
                Parent parentResult = mapRowToParent(results);
                parents.add(parentResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return parents;
    }
    public Parent createParent(Parent parent) {
        Parent newParent = null;

        String sql = "INSERT INTO parent (first_name, last_name, address, phone_number) " +
                "VALUES (?, ?, ?, ?) RETURNING parent_id;";
        try {
            int newParentId = jdbcTemplate.queryForObject(sql, int.class, parent.getFirst_name(),
                    parent.getLast_name(), parent.getAddress(), parent.getPhone_number());
            newParent = getParentById(newParentId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return  newParent;
    }
    public Parent updateParent(Parent parent) {
        Parent updatedParent = null;

        String sql = "UPDATE parent SET first_name = ?, last_name = ?, address = ?, phone_number = ? " +
                "WHERE parent_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, parent.getFirst_name(), parent.getLast_name(),
                    parent.getAddress(), parent.getPhone_number(), parent.getId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedParent = getParentById(parent.getId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedParent;
    }
    public int deleteParentById(int id) {
        int numberOfRows = 0;
        String deleteStudentParentSql = "DELETE FROM student_parent WHERE parent_id = ?;";
        String deleteParentSql = "DELETE FROM parent WHERE parent_id = ?;";
        try {
            jdbcTemplate.update(deleteStudentParentSql, id);
            numberOfRows = jdbcTemplate.update(deleteParentSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    public Parent mapRowToParent (SqlRowSet result) {
        Parent parent = new Parent();
        parent.setId(result.getInt("parent_id"));
        parent.setFirst_name(result.getString("first_name"));
        parent.setLast_name(result.getString("last_name"));
        parent.setAddress(result.getString("address"));
        parent.setPhone_number(result.getString("phone_number"));
        return parent;
    }
}
