package org.example.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.exception.DaoException;
import org.example.models.Discipline;
import org.example.models.Attendance;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JdbcDisciplineDAO {
    private JdbcTemplate jdbcTemplate;
    public JdbcDisciplineDAO(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Discipline> getAllDisciplines() {
        List<Discipline> disciplineList = new ArrayList<>();

        String sql = "SELECT * FROM discipline";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Discipline discipline = mapRowToDiscipline(results);
                disciplineList.add(discipline);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return disciplineList;
    }
    public Discipline getDisciplineById(int id) {
        Discipline discipline = null;
        String sql = "SELECT * FROM discipline WHERE discipline_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                discipline = mapRowToDiscipline(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return discipline;
    }
    public List<Discipline> getDisciplineByStudentId(int id) {
        List<Discipline> referrals = new ArrayList<>();
        String sql = "SELECT * FROM discipline WHERE student_id IN ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                Discipline disciplineResult = mapRowToDiscipline(results);
                referrals.add(disciplineResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
             throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return referrals;
    }
    public Discipline getDisciplineByStudentName(String lastName, String firstName) {
        Discipline discipline = null;
        String sql = "SELECT * FROM discipline JOIN student ON student.student_id = discipline.student_id WHERE last_name = ? AND first_name = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, lastName, firstName);
            while (results.next()) {
                Discipline disciplineResult = mapRowToDiscipline(results);
                discipline = mapRowToDiscipline(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return discipline;
    }
    public Discipline createDiscipline(Discipline discipline) {
        Discipline newDiscipline = null;
        String sql = "INSERT INTO discipline (student_id, referral_date, description, discipline_action, parent_contacted) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING discipline_id;";
        try{
            int newDisciplineId = jdbcTemplate.queryForObject(sql, int.class, discipline.getStudentId(), discipline.getReferralDate(),
                    discipline.getDescription(), discipline.getDisciplineAction(), discipline.isParentContacted());
            newDiscipline = getDisciplineById(newDisciplineId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newDiscipline;
    }
    public Discipline updateDiscipline(Discipline discipline) {
        Discipline updatedDiscipline = null;
        String sql = "UPDATE discipline SET student_id = ?, referral_date = ?, description = ?, discipline_action = ?, parent_contacted = ? " +
                "WHERE discipline_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, discipline.getStudentId(), discipline.getReferralDate(), discipline.getDescription(),
                    discipline.getDisciplineAction(), discipline.isParentContacted(), discipline.getId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedDiscipline = getDisciplineById(discipline.getId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedDiscipline;
    }
    public int deleteDisciplineById(int id) {
        int numberOfRows = 0;
        String deleteDisciplineSql = "DELETE FROM discipline WHERE discipline_id = ?;";
        String deleteStudentSql = "UPDATE student SET discipline_id = null WHERE discipline_id = ?;";
        try {
            jdbcTemplate.update(deleteStudentSql, id);
            numberOfRows = jdbcTemplate.update(deleteDisciplineSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    public Discipline mapRowToDiscipline (SqlRowSet result) {
        Discipline discipline = new Discipline();
        discipline.setId(result.getInt("discipline_id"));
        discipline.setStudentId(result.getInt("student_id"));
        Date referralDate = result.getDate("referral_date");
        discipline.setReferralDate(referralDate != null ? referralDate.toLocalDate() : null);
        discipline.setDescription(result.getString("description"));
        discipline.setDisciplineAction(result.getString("discipline_action"));
        discipline.setParentContacted(result.getBoolean("parent_contacted"));
        return discipline;
    }
}
