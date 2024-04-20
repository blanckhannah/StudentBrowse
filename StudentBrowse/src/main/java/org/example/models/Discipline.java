package org.example.models;

import java.time.LocalDate;

public class Discipline {
    private int id;
    private int studentId;
    private LocalDate referralDate;
    private String description;
    private String disciplineAction;
    private boolean parentContacted;

    public Discipline(int id, int studentId, LocalDate referralDate, String description, String disciplineAction, boolean parentContacted) {
        this.id = id;
        this.studentId = studentId;
        this.referralDate = referralDate;
        this.description = description;
        this.disciplineAction = disciplineAction;
        this.parentContacted = parentContacted;
    }
    public Discipline() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public LocalDate getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(LocalDate referralDate) {
        this.referralDate = referralDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisciplineAction() {
        return disciplineAction;
    }

    public void setDisciplineAction(String disciplineAction) {
        this.disciplineAction = disciplineAction;
    }

    public boolean isParentContacted() {
        return parentContacted;
    }

    public void setParentContacted(boolean parentContacted) {
        this.parentContacted = parentContacted;
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", referralDate=" + referralDate +
                ", description='" + description + '\'' +
                ", disciplineAction='" + disciplineAction + '\'' +
                ", parentContacted=" + parentContacted +
                '}';
    }
}
