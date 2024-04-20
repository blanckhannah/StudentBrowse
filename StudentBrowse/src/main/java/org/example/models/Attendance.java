package org.example.models;

import java.time.LocalDate;

public class Attendance {
    private int id;
    private LocalDate attendanceDate;
    private boolean present;
    private boolean tardy;
    private int student_id;

    public Attendance(int id, LocalDate attendanceDate, boolean present, boolean tardy, int student_id) {
        this.id = id;
        this.attendanceDate = attendanceDate;
        this.present = present;
        this.tardy = tardy;
        this.student_id = student_id;
    }
    public Attendance(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isTardy() {
        return tardy;
    }

    public void setTardy(boolean tardy) {
        this.tardy = tardy;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", attendanceDate=" + attendanceDate +
                ", present=" + present +
                ", tardy=" + tardy +
                ", student_id=" + student_id +
                '}';
    }
}
