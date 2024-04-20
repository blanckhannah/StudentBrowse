package org.example.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.exception.DaoException;
import org.example.models.Attendance;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JdbcAttendanceDAO {
    private JdbcTemplate jdbcTemplate;
    public JdbcAttendanceDAO(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Attendance> getAllAttendances() {
        List<Attendance> attendanceList = new ArrayList<>();

        String sql = "SELECT * FROM attendance";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Attendance attendance = mapRowToAttendance(results);
                attendanceList.add(attendance);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return attendanceList;
    }
    public Attendance getAttendanceById(int id) {
        Attendance attendance = null;
        String sql = "SELECT * FROM attendance WHERE attendance_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                attendance = mapRowToAttendance(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return attendance;
    }
    public List<Attendance> getAttendanceByStudentId(int id) {
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE student_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                Attendance attendanceResult = mapRowToAttendance(results);
                attendanceList.add(attendanceResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return attendanceList;
    }
    public List<Attendance> getAttendanceByStudentName(String lastName, String firstName) {
        List <Attendance> attendances = null;
        String sql = "SELECT * FROM attendance JOIN student ON student.student_id = attendance.student_id WHERE last_name = ? AND first_name = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, lastName, firstName);
            while (results.next()) {
                Attendance attendanceResult = mapRowToAttendance(results);
                attendances.add(attendanceResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return attendances;
    }
    public Attendance createAttendance(Attendance attendance) {
        Attendance newAttendance = null;
        String sql = "INSERT INTO attendance (attendance_date, present, tardy, student_id) " +
                "VALUES (?, ?, ?, ?) RETURNING attendance_id;";
        try {
            int newAttendanceId = jdbcTemplate.queryForObject(sql, int.class, attendance.getAttendanceDate(),
                    attendance.isPresent(), attendance.isTardy(), attendance.getStudent_id());
            newAttendance = getAttendanceById(newAttendanceId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newAttendance;
    }
    public Attendance updateAttendance(Attendance attendance) {
        Attendance updatedAttendance = null;
        String sql = "UPDATE attendance SET attendance_date = ?, present = ?, tardy = ?, student_id = ? " +
                "WHERE attendance_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, attendance.getAttendanceDate(), attendance.isPresent(),
                    attendance.isTardy(), attendance.getStudent_id(), attendance.getId());
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedAttendance = getAttendanceById(attendance.getId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedAttendance;
    }
    public int deleteAttendanceById(int id) {
        int numberOfRows = 0;
        String deleteAttendanceSql = "DELETE FROM attendance WHERE attendance_id = ?;";
        String deleteStudentSql = "UPDATE student SET attendance_id = null WHERE attendance_id = ?;";
        try {
            jdbcTemplate.update(deleteStudentSql, id);
            numberOfRows = jdbcTemplate.update(deleteAttendanceSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    public Attendance mapRowToAttendance (SqlRowSet result) {
        Attendance attendance = new Attendance();
        attendance.setId(result.getInt("attendance_id"));
        Date attendanceDate = result.getDate("attendance_date");
        attendance.setAttendanceDate(attendanceDate != null ? attendanceDate.toLocalDate() : null);
        attendance.setPresent(result.getBoolean("present"));
        attendance.setTardy(result.getBoolean("tardy"));
        attendance.setStudent_id(result.getInt("student_id"));
        return attendance;
    }
}

