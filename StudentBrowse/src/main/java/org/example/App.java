package org.example;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dao.*;
import org.example.exception.DaoException;
import org.example.models.*;
import org.example.view.Menu;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class App {
    public static final String RESET = "\u001B[0m";
    public static final String	WHITE	= "\u001B[37m";
    public static final String	RED	    = "\u001B[31m";
    public static final String	GREEN	= "\u001B[32m";
    public static final String	YELLOW	= "\u001B[33m";
    public static final String	BLUE	= "\u001B[34m";
    public static final String	MAGENTA	= "\u001B[35m";
    public static final String	CYAN	= "\u001B[36m";
    private static final String MAIN_MENU_STUDENT = RESET + "Student Management" + RESET;
    private static final String MAIN_MENU_TEACHER = RESET + "Teacher Management" + RESET;
    private static final String MAIN_MENU_PARENT = RESET + "Parent Management" + RESET;
    private static final String MAIN_MENU_ATTENDANCE = RESET + "Attendance Records" + RESET;
    private static final String MAIN_MENU_DISCIPLINE = RESET + "Discipline Records" + RESET;
    private static final String MAIN_MENU_EXIT = RESET + "Exit" + RESET;
    private static final String[] MAIN_MENU_OPTIONS = new String[] {MAIN_MENU_STUDENT,
                                                                    MAIN_MENU_TEACHER,
                                                                    MAIN_MENU_PARENT,
                                                                    MAIN_MENU_ATTENDANCE,
                                                                    MAIN_MENU_DISCIPLINE,
                                                                    MAIN_MENU_EXIT };
    private static final String MENU_RETURN_TO_MAIN ="Return to main menu";
    private static final String STUDENT_MENU_ALL_STUDENTS = RED + "View all students" + RED;
    private static final String STUDENT_MENU_SEARCH_BY_NAME = RED + "Search Student by last name" + RED;
    private static final String STUDENT_MENU_SEARCH_BY_TEACHER = RED + "View students by class" + RED;
    private static final String STUDENT_MENU_CREATE_STUDENT = RED + "Add new student" + RED;
    private static final String STUDENT_MENU_UPDATE_STUDENT = RED + "Update student information" + RED;
    private static final String STUDENT_MENU_DELETE_STUDENT = RED + "Remove student" + RED;
    private static final String[] STUDENT_MENU_OPTIONS = new String[] { STUDENT_MENU_ALL_STUDENTS,
                                                                        STUDENT_MENU_SEARCH_BY_NAME,
                                                                        STUDENT_MENU_SEARCH_BY_TEACHER,
                                                                        STUDENT_MENU_CREATE_STUDENT,
                                                                        STUDENT_MENU_UPDATE_STUDENT,
                                                                        STUDENT_MENU_DELETE_STUDENT,
                                                                        MENU_RETURN_TO_MAIN };
    private static final String TEACHER_MENU_ALL_TEACHERS = BLUE + "View all teachers" + BLUE;
    private static final String TEACHER_MENU_SEARCH_BY_NAME = BLUE + "Search teacher by last name" + BLUE;
    private static final String TEACHER_MENU_CREATE_TEACHER = BLUE + "Add new teacher" + BLUE;
    private static final String TEACHER_MENU_UPDATE_TEACHER = BLUE + "Update Teacher information" + BLUE;
    private static final String TEACHER_MENU_DELETE_TEACHER = BLUE + "Remove teacher" + BLUE;
    private static final String[] TEACHER_MENU_OPTIONS = new String[] { TEACHER_MENU_ALL_TEACHERS,
                                                                        TEACHER_MENU_SEARCH_BY_NAME,
                                                                        TEACHER_MENU_CREATE_TEACHER,
                                                                        TEACHER_MENU_UPDATE_TEACHER,
                                                                        TEACHER_MENU_DELETE_TEACHER,
                                                                        MENU_RETURN_TO_MAIN };
    private static final String PARENT_MENU_ALL_PARENTS = YELLOW + "View all parents" + YELLOW;
    private static final String PARENT_MENU_SEARCH_BY_NAME = YELLOW + "Search parents by last name" + YELLOW;
    private static final String PARENT_MENU_CREATE_PARENT = YELLOW + "Add parent to student" + YELLOW;
    private static final String PARENT_MENU_UPDATE_PARENT = YELLOW + "Update parent information" + YELLOW;
    private static final String PARENT_MENU_DELETE_PARENT = YELLOW + "Remove parent from student" + YELLOW;
    private static final String[] PARENT_MENU_OPTIONS = new String[] { PARENT_MENU_ALL_PARENTS,
                                                                        PARENT_MENU_SEARCH_BY_NAME,
                                                                        PARENT_MENU_CREATE_PARENT,
                                                                        PARENT_MENU_UPDATE_PARENT,
                                                                        PARENT_MENU_DELETE_PARENT,
                                                                        MENU_RETURN_TO_MAIN };
    private static final String ATTENDANCE_MENU_VIEW_ALL = MAGENTA + "View all attendance records" + MAGENTA;
    private static final String ATTENDANCE_MENU_BY_STUDENT = MAGENTA + "View attendance by student" + MAGENTA;
    private static final String ATTENDANCE_MENU_CREATE = MAGENTA + "Add attendance record" + MAGENTA;
    private static final String ATTENDANCE_MENU_UPDATE = MAGENTA + "Alter attendance" + MAGENTA;
    private static final String ATTENDANCE_MENU_DELETE = MAGENTA + "Delete attendance record" + MAGENTA;
    private static final String[] ATTENDANCE_MENU_OPTIONS = new String[] { ATTENDANCE_MENU_VIEW_ALL,
                                                                            ATTENDANCE_MENU_BY_STUDENT,
                                                                            ATTENDANCE_MENU_CREATE,
                                                                            ATTENDANCE_MENU_UPDATE,
                                                                            ATTENDANCE_MENU_DELETE,
                                                                            MENU_RETURN_TO_MAIN };
    private static final String DISCIPLINE_MENU_VIEW_ALL = CYAN + "View all referrals" + CYAN;
    private static final String DISCIPLINE_MENU_BY_STUDENT = CYAN + "View referrals by student" + CYAN;
    private static final String DISCIPLINE_MENU_CREATE = CYAN + "Write new referral" + CYAN;
    private static final String DISCIPLINE_MENU_UPDATE = CYAN + "Edit referral" + CYAN;
    private static final String DISCIPLINE_MENU_DELETE = CYAN + "Delete referral" + CYAN;
    private static final String[] DISCIPLINE_MENU_OPTIONS = new String[] { DISCIPLINE_MENU_VIEW_ALL,
                                                                            DISCIPLINE_MENU_BY_STUDENT,
                                                                            DISCIPLINE_MENU_CREATE,
                                                                            DISCIPLINE_MENU_UPDATE,
                                                                            DISCIPLINE_MENU_DELETE,
                                                                            MENU_RETURN_TO_MAIN };
    private final Menu menu;
    private final JdbcStudentDAO jdbcStudentDao;
    private final JdbcTeacherDAO jdbcTeacherDao;
    private final JdbcParentDAO jdbcParentDao;
    private final JdbcAttendanceDAO jdbcAttendanceDao;
    private final JdbcDisciplineDAO jdbcDisciplineDao;
    public static void main( String[] args ) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/StudentBrowse");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        App application = new App(dataSource);
        application.run();
    }
    public App(BasicDataSource dataSource) {
        this.menu = new Menu(System.in, System.out);

        jdbcStudentDao = new JdbcStudentDAO(dataSource);
        jdbcTeacherDao = new JdbcTeacherDAO(dataSource);
        jdbcParentDao = new JdbcParentDAO(dataSource);
        jdbcAttendanceDao = new JdbcAttendanceDAO(dataSource);
        jdbcDisciplineDao = new JdbcDisciplineDAO(dataSource);
    }
    private void run() {
        boolean running = true;
        while (running) {
            printHeading("Main Menu");
            String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (choice.equals(MAIN_MENU_STUDENT)) {
                runStudents();
            } else if (choice.equals(MAIN_MENU_TEACHER)) {
                runTeachers();
            } else if (choice.equals(MAIN_MENU_PARENT)) {
                runParents();
            } else if (choice.equals(MAIN_MENU_ATTENDANCE)) {
                runAttendance();
            } else if (choice.equals(MAIN_MENU_DISCIPLINE)) {
                runDiscipline();
            } else if (choice.equals(MAIN_MENU_EXIT)) {
                running = false;
            }
        }
    }

    //STUDENTS ______________________________________________________________________________________________

    private void runStudents() {
        try {
            printHeading("Students");
            String choice = (String)menu.getChoiceFromOptions(STUDENT_MENU_OPTIONS);
            if(choice.equals(STUDENT_MENU_ALL_STUDENTS)) {
                runGetAllStudents();
            } else if (choice.equals(STUDENT_MENU_SEARCH_BY_NAME)) {
                runGetStudentsByName();
            } else if (choice.equals(STUDENT_MENU_SEARCH_BY_TEACHER)) {
                runGetStudentsByTeacher();
            } else if (choice.equals(STUDENT_MENU_CREATE_STUDENT)) {
                runCreateStudent();
            } else if (choice.equals(STUDENT_MENU_UPDATE_STUDENT)) {
                runUpdateStudent();
            } else if (choice.equals(STUDENT_MENU_DELETE_STUDENT)) {
                runDeleteStudent();
            }
        } catch (DaoException e) {
            System.err.println("Student Error Occurred: " + e.getMessage());
        }
    }
    private void runGetAllStudents() {
        printHeading("All Students");
        List<Student> allStudents = jdbcStudentDao.getAllStudents();
        listStudents(allStudents);
    }
    private void runGetStudentsByName() {
        printHeading("Student Search");
        String lastName = getUserInput("Enter student's last name: ");
        List<Student> students = jdbcStudentDao.getStudentsByLastName(lastName);
        listStudents(students);
    }
    private void runGetStudentsByTeacher() {
        printHeading("Class Roster Search");
        String teacherName = getUserInput("Enter Teacher's last name: ");
        List<Student> studentsInClass = jdbcStudentDao.getStudentsByTeacherName(teacherName);
        listStudents(studentsInClass);
    }
    private void runCreateStudent() {
        printHeading("Add New Student");
        Student newStudent = new Student();

        String newFirstName = getUserInput("Enter the new student's first name: ");
        String newLastName = getUserInput("Enter the new student's last name: ");
        String newAddress = getUserInput("Enter the new student's address: ");
        int newGrade = Integer.parseInt(getUserInput("Enter the new Student's grade: "));
        newStudent.setFirst_name(newFirstName);
        newStudent.setLast_name(newLastName);
        newStudent.setAddress(newAddress);
        newStudent.setGrade(newGrade);
        newStudent = jdbcStudentDao.createStudent(newStudent);
        System.out.println(newStudent.getFirst_name() + " " + newStudent.getLast_name() + "  added");

    }
    private void runUpdateStudent() {
        printHeading("Update Student Information");
        String lastName = getUserInput("Enter the last name of student to update: ");
        List<Student> lastNameStudents = jdbcStudentDao.getStudentsByLastName(lastName);
        if (lastNameStudents.size() > 0 ) {
            System.out.println("Choose a student to update");
            Student selectedStudent = (Student) menu.getChoiceFromOptions(lastNameStudents.toArray());

            String newFirstName = getUserInput("Enter student's updated first name (leave blank to skip): ");
            String newLastName = getUserInput("Enter student's updated last name (leave blank to skip): ");
            String newAddress = getUserInput("Enter student's updated address (leave blank to skip): ");
            String newGrade = getUserInput("Enter student's updated grade (leave blank to skip): ");
            if (!newFirstName.equals("")) {
                selectedStudent.setFirst_name(newFirstName);
            }
            if(!newLastName.equals("")) {
                selectedStudent.setLast_name(newLastName);
            }
            if (!newAddress.equals("")) {
                selectedStudent.setAddress(newAddress);
            }
            if (!newGrade.equals("")) {
                selectedStudent.setGrade(Integer.parseInt(newGrade));
            }
            selectedStudent = jdbcStudentDao.updateStudent(selectedStudent);
            System.out.println(selectedStudent.getFirst_name() + " " + selectedStudent.getLast_name() + " updated");
        } else {
            System.out.println("No results");
        }
    }
    private void runDeleteStudent() {
        printHeading("Remove Student");
        String lastName = getUserInput("Enter the last name of student to remove: ");
        List<Student> lastNameStudents = jdbcStudentDao.getStudentsByLastName(lastName);
        if (lastNameStudents.size() > 0 ) {
            System.out.println("Choose a student to remove");
            Student selectedStudent = (Student) menu.getChoiceFromOptions(lastNameStudents.toArray());
            jdbcStudentDao.deleteStudentById(selectedStudent.getId());
            System.out.println(selectedStudent.getFirst_name() + " " + selectedStudent.getLast_name() + " deleted");
        } else {
            System.out.println("No results");
        }
    }
    private void listStudents(List<Student> students) {
        System.out.println();
        if(students.size() > 0) {
            for(Student student : students) {
                System.out.println(student);
            }
        } else {
            System.out.println("*No results*");
        }
    }

    //TEACHERS______________________________________________________________________________________________

    private void runTeachers() {
        try {
            printHeading("Teachers");
            String choice = (String)menu.getChoiceFromOptions(TEACHER_MENU_OPTIONS);
            if(choice.equals(TEACHER_MENU_ALL_TEACHERS)) {
                runGetAllTeachers();
            } else if (choice.equals(TEACHER_MENU_SEARCH_BY_NAME)) {
                runGetTeachersByName();
            } else if (choice.equals(TEACHER_MENU_CREATE_TEACHER)) {
                runCreateTeacher();
            } else if (choice.equals(TEACHER_MENU_UPDATE_TEACHER)) {
                runUpdateTeacher();
            } else if (choice.equals(TEACHER_MENU_DELETE_TEACHER)) {
                runDeleteTeacher();
            }
        } catch (DaoException e) {
            System.err.println("Error Occurred: " + e.getMessage());
        }
    }
    private void runGetAllTeachers() {
        printHeading("All Teachers");
        List<Teacher> allTeachers = jdbcTeacherDao.getAllTeachers();
        listTeachers(allTeachers);
    }
    private void runGetTeachersByName() {
        printHeading("Teacher Search");
        String lastName = getUserInput("Enter the teacher's last name: ");
        List<Teacher> teachers = jdbcTeacherDao.getTeacherByLastName(lastName);
        listTeachers(teachers);
    }
    private void runCreateTeacher() {
        printHeading("Add New Teacher");
        Teacher newTeacher = new Teacher();

        String newFirstName = getUserInput("Enter the new teacher's first name: ");
        String newLastName = getUserInput("Enter the new teacher's last name: ");
        String newEmail = getUserInput("Enter the new teacher's email: ");
        newTeacher.setFirst_name(newFirstName);
        newTeacher.setLast_name(newLastName);
        newTeacher.setEmail(newEmail);
        newTeacher = jdbcTeacherDao.createTeacher(newTeacher);
        System.out.println(newTeacher.getFirst_name() + " " + newTeacher.getLast_name() + " added");
    }
    private void runUpdateTeacher() {
        printHeading("Update Teacher Information");
        String lastName = getUserInput("Enter the last name of the teacher to update: ");
        List<Teacher> lastNameTeachers = jdbcTeacherDao.getTeacherByLastName(lastName);
        if (lastNameTeachers.size() > 0 ) {
            System.out.println("Choose a teacher to update: ");
            Teacher selectedTeacher = (Teacher) menu.getChoiceFromOptions(lastNameTeachers.toArray());

            String newFirstName = getUserInput("Enter teacher's updated first name (leave blank to skip): ");
            String newLastName = getUserInput("Enter teacher's updated lastName (leave blank to skip): ");
            String newEmail = getUserInput("Enter teacher's updated email (leave blank to skip): ");
            if (!newFirstName.equals("")) {
                selectedTeacher.setFirst_name(newFirstName);
            }
            if (!newLastName.equals("")) {
                selectedTeacher.setLast_name(newLastName);
            }
            if (!newEmail.equals("")) {
                selectedTeacher.setEmail(newEmail);
            }
            selectedTeacher = jdbcTeacherDao.updateTeacher(selectedTeacher);
            System.out.println(selectedTeacher.getFirst_name() + " " + selectedTeacher.getLast_name() + " updated");
        } else {
            System.out.println("No results");
        }
    }
    private void runDeleteTeacher() {
        printHeading("Remove Teacher");
        String lastName = getUserInput("Enter the last name of the Teacher to remove: ");
        List<Teacher> lastNameTeachers = jdbcTeacherDao.getTeacherByLastName(lastName);
        if (lastNameTeachers.size() > 0) {
            System.out.println("Choose a teacher to remove: ");
            Teacher selectedTeacher = (Teacher)menu.getChoiceFromOptions(lastNameTeachers.toArray());
            jdbcTeacherDao.deleteTeacherById(selectedTeacher.getId());
            System.out.println(selectedTeacher.getFirst_name() + " " + selectedTeacher.getLast_name() + " deleted");
        } else {
            System.out.println("No results");
        }
    }
    private void listTeachers(List<Teacher> teachers) {
        System.out.println();
        if(teachers.size() > 0) {
            for(Teacher teacher : teachers) {
                System.out.println(teacher);
            }
        } else {
            System.out.println("*No results*");
        }
    }

    //PARENTS______________________________________________________________________________________________

    private void runParents() {
        try {
            printHeading("Parents");
            String choice = (String)menu.getChoiceFromOptions(PARENT_MENU_OPTIONS);
            if(choice.equals(PARENT_MENU_ALL_PARENTS)) {
                runGetAllParents();
            } else if (choice.equals(PARENT_MENU_SEARCH_BY_NAME)) {
                runGetParentByName();
            } else if (choice.equals(PARENT_MENU_CREATE_PARENT)) {
                runCreateParent();
            } else if (choice.equals(PARENT_MENU_UPDATE_PARENT)) {
                runUpdateParent();
            } else if (choice.equals(PARENT_MENU_DELETE_PARENT)) {
                runDeleteParent();
            }
        } catch (DaoException e) {
            System.err.println("Parent Error Occurred: " + e.getMessage());
        }
    }
    private void runGetAllParents() {
        printHeading("All Parents");
        List<Parent> allParents = jdbcParentDao.getAllParents();
        listParents(allParents);
    }
    private void runGetParentByName() {
        printHeading("Parent Search");
        String lastName = getUserInput("Enter the parent's last name: ");
        List<Parent> parents = jdbcParentDao.getParentsByLastName(lastName);
        listParents(parents);
    }
    private void runCreateParent() {
        printHeading("Add New Parent");
        Parent newParent = new Parent();

        String newFirstName = getUserInput("Enter the new parent's first name: ");
        String newLastName = getUserInput("Enter the new parent's last name: ");
        String newAddress = getUserInput("Enter the new parent's address: ");
        String newPhoneNumber = getUserInput("Enter the new parent's phone_number: ");
        newParent.setFirst_name(newFirstName);
        newParent.setLast_name(newLastName);
        newParent.setAddress(newAddress);
        newParent.setPhone_number(newPhoneNumber);
        newParent = jdbcParentDao.createParent(newParent);
        System.out.println(newParent.getFirst_name() + " " + newParent.getLast_name() + " added");
    }
    private void runUpdateParent(){
        printHeading("Update Parent Information");
        String lastName = getUserInput("Enter the last name of the parent to update: ");
        List<Parent> lastNameParents = jdbcParentDao.getParentsByLastName(lastName);
        if (lastNameParents.size() > 0) {
            System.out.println("Choose a parent to update: ");
            Parent selectedParent = (Parent) menu.getChoiceFromOptions(lastNameParents.toArray());

            String newFirstName = getUserInput("Enter parent's updated first name (leave blank to skip): ");
            String newLastName = getUserInput("Enter parent's updated last name (leave blank to skip): ");
            String newAddress = getUserInput("Enter parent's updated address (leave blank to skip): ");
            String newPhoneNumber = getUserInput("Enter parent's updated phone number (leave blank to skip): ");
            if(!newFirstName.equals("")) {
                selectedParent.setFirst_name(newFirstName);
            }
            if (!newLastName.equals("")) {
                selectedParent.setLast_name(newLastName);
            }
            if (!newAddress.equals("")) {
                selectedParent.setAddress(newAddress);
            }
            if (!newPhoneNumber.equals("")) {
                selectedParent.setPhone_number(newPhoneNumber);
            }
            selectedParent = jdbcParentDao.updateParent(selectedParent);
            System.out.println(selectedParent.getFirst_name() + " " + selectedParent.getLast_name() + " updated");
        } else {
            System.out.println("No results");
        }
    }
    private void runDeleteParent() {
        printHeading("Remove Parent");
        String lastName = getUserInput("Enter the last name of the parent to remove: ");
        List<Parent> lastNameParents = jdbcParentDao.getParentsByLastName(lastName);
        if(lastNameParents.size() > 0) {
            System.out.println("Choose a parent to remove: ");
            Parent selectedParent = (Parent) menu.getChoiceFromOptions(lastNameParents.toArray());
            jdbcParentDao.deleteParentById(selectedParent.getId());
            System.out.println(selectedParent.getFirst_name() + " " + selectedParent.getLast_name() + " removed");
        } else {
            System.out.println("No results");
        }
    }
    private void listParents(List<Parent> parents) {
        System.out.println();
        if (parents.size() > 0 ) {
            for (Parent parent : parents) {
                System.out.println(parent);
            }
        } else {
            System.out.println("*No results*");
        }
    }

    //ATTENDANCE______________________________________________________________________________________________

    private void runAttendance() {
        try {
            printHeading("Attendance");
            String choice = (String)menu.getChoiceFromOptions(ATTENDANCE_MENU_OPTIONS);
            if(choice.equals(ATTENDANCE_MENU_VIEW_ALL)) {
                runGetAllAttendance();
            } else if (choice.equals(ATTENDANCE_MENU_BY_STUDENT)) {
                runGetAttendanceByStudent();
            } else if (choice.equals(ATTENDANCE_MENU_CREATE)) {
                runCreateAttendance();
            } else if (choice.equals(ATTENDANCE_MENU_UPDATE)) {
                runUpdateAttendance();
            } else if (choice.equals(ATTENDANCE_MENU_DELETE)) {
                runDeleteAttendance();
            }
        } catch (DaoException e) {
            System.err.println("Attendance Error Occurred: " + e.getMessage());
        }
    }
    private void runGetAllAttendance() {
        printHeading("All Attendance");
        List<Attendance> allAttendance = jdbcAttendanceDao.getAllAttendances();
        listAttendance(allAttendance);
    }
    private void runGetAttendanceByStudent() {
        printHeading("Attendance Search By Id");
        String id = getUserInput("Enter student's id for attendance records: ");
        List<Attendance> attendanceList = jdbcAttendanceDao.getAttendanceByStudentId(Integer.parseInt(id));
        listAttendance(attendanceList);
    }
    private void runCreateAttendance() {
        printHeading("Add New Attendance Record");
        Attendance newAttendance = new Attendance();
        String lastName = getUserInput("Enter the last name of the student: ");
        List<Student> allStudents = jdbcStudentDao.getStudentsByLastName(lastName);
        if(allStudents.size() > 0) {
            System.out.println("Choose a student to add an attendance record: ");
            Student selectedStudent = (Student)menu.getChoiceFromOptions(allStudents.toArray());
            newAttendance.setStudent_id(selectedStudent.getId());
        }
        String newAttendanceDate = getUserInput("Enter the date (YYYY-MM-DD): ");
        String newPresent = getUserInput("Was the student present? (true or false): ");
        String newTardy = getUserInput("Was the student tardy? (true or false): ");
        newAttendance.setAttendanceDate(LocalDate.parse(newAttendanceDate));
        newAttendance.setPresent(Boolean.parseBoolean(newPresent));
        newAttendance.setTardy(Boolean.parseBoolean(newTardy));
        newAttendance = jdbcAttendanceDao.createAttendance(newAttendance);
        System.out.println("Attendance record created for student #" + newAttendance.getStudent_id());
    }
    private void runUpdateAttendance() {
        printHeading("Update Attendance Record");
        String lastName = getUserInput("Enter student's lastName: ");
        List<Student> students = jdbcStudentDao.getStudentsByLastName(lastName);
        if (students.size() > 0) {
            System.out.println("Choose a student to update attendance: ");
            Student selectedStudent = (Student) menu.getChoiceFromOptions(students.toArray());

            List <Attendance> attendanceDays = jdbcAttendanceDao.getAttendanceByStudentId(selectedStudent.getId());

            System.out.println("Choose a record to alter: ");
            Attendance selectedAttendance = (Attendance) menu.getChoiceFromOptions(attendanceDays.toArray());
            selectedAttendance.setStudent_id(selectedStudent.getId());

            String newDate = getUserInput("Enter the updated date (leave blank to skip): ");
            String newPresent = getUserInput("Update presence (true or false) (leave blank to skip)");
            String newTardy = getUserInput("Update tardiness (true or false) (leave blank to skip)");
            if (!newDate.equals("")) {
                selectedAttendance.setAttendanceDate(LocalDate.parse(newDate));
            }
            if (!newPresent.equals("")) {
                selectedAttendance.setPresent(Boolean.parseBoolean(newPresent));
            }
            if (!newTardy.equals("")) {
                selectedAttendance.setTardy(Boolean.parseBoolean(newTardy));
            }
            selectedAttendance = jdbcAttendanceDao.updateAttendance(selectedAttendance);
            System.out.println("Attendance updated for student #" + selectedAttendance.getStudent_id());
        } else {
            System.out.println("No results");
        }
    }
    private void runDeleteAttendance() {
        printHeading("Remove Attendance Record");
        String lastName = getUserInput("Enter student's lastName: ");
        List<Student> students = jdbcStudentDao.getStudentsByLastName(lastName);
        if (students.size() > 0) {
            System.out.println("Choose a student to update attendance: ");
            Student selectedStudent = (Student) menu.getChoiceFromOptions(students.toArray());

            List<Attendance> attendanceDays = jdbcAttendanceDao.getAttendanceByStudentId(selectedStudent.getId());

            System.out.println("Choose a record to delete: ");

            Attendance selectedAttendance = (Attendance)menu.getChoiceFromOptions(attendanceDays.toArray());
            jdbcAttendanceDao.deleteAttendanceById(selectedAttendance.getId());
            System.out.println("Attendance record for " + selectedAttendance.getAttendanceDate() + " for " + selectedStudent.getFirst_name() +
                    " " + selectedStudent.getLast_name() + " has been deleted");
        } else {
            System.out.println("No results");
        }
    }
    private void listAttendance(List<Attendance> attendances) {
        System.out.println();
        if (attendances.size() > 0) {
            for (Attendance attendance : attendances) {
                System.out.println(attendance);
            }
        } else {
            System.out.println("No results");
        }
    }

    //DISCIPLINE______________________________________________________________________________________________

    private void runDiscipline() {
        try {
            printHeading("Discipline");
            String choice = (String)menu.getChoiceFromOptions(DISCIPLINE_MENU_OPTIONS);
            if(choice.equals(DISCIPLINE_MENU_VIEW_ALL)) {
               runGetAllReferrals();
            } else if (choice.equals(DISCIPLINE_MENU_BY_STUDENT)) {
                runGetReferralsByStudent();
            } else if (choice.equals(DISCIPLINE_MENU_CREATE)) {
               runCreateReferral();
            } else if (choice.equals(DISCIPLINE_MENU_UPDATE)) {
                runUpdateReferral();
            } else if (choice.equals(DISCIPLINE_MENU_DELETE)) {
               runDeleteReferral();
            }
        } catch (DaoException e) {
            System.err.println("Discipline Error Occurred: " + e.getMessage());
        }
    }
    private void runGetAllReferrals() {
        printHeading("All Referrals");
        List<Discipline> allReferrals = jdbcDisciplineDao.getAllDisciplines();
        listDiscipline(allReferrals);
    }
    private void runGetReferralsByStudent() {
        printHeading("Referrals By Student");
        String lastName = getUserInput("Enter the student's last name: ");
        List<Student> students = jdbcStudentDao.getStudentsByLastName(lastName);
        if (students.size() > 0 ) {
            System.out.println("choose a student to view referrals: ");
            Student selectedStudent = (Student)menu.getChoiceFromOptions(students.toArray());
            List<Discipline> disciplineList = jdbcDisciplineDao.getDisciplineByStudentId(selectedStudent.getId());
            listDiscipline(disciplineList);
        } else {
            System.out.println("No results");
        }
    }
    private void runCreateReferral() {
        printHeading("Write Referral");
        Discipline newDiscipline = new Discipline();
        String lastName = getUserInput("Enter the last name of the student: ");
        List<Student> allStudents = jdbcStudentDao.getStudentsByLastName(lastName);
        if (allStudents.size() > 0 ) {
            System.out.println("Choose a student to write a referral for: ");
            Student selectedStudent = (Student) menu.getChoiceFromOptions(allStudents.toArray());
            newDiscipline.setStudentId(selectedStudent.getId());
        }
        String newReferralDate = getUserInput("Enter the date of the referral (YYYY-MM-DD): ");
        String newDescription = getUserInput("Describe the incident: ");
        String newDisciplineAction = getUserInput("Action taken: ");
        String newParentContacted = getUserInput("Was the parent contacted? (true or false): ");
        newDiscipline.setReferralDate(LocalDate.parse(newReferralDate));
        newDiscipline.setDescription(newDescription);
        newDiscipline.setDisciplineAction(newDisciplineAction);
        newDiscipline.setParentContacted(Boolean.parseBoolean(newParentContacted));
        newDiscipline = jdbcDisciplineDao.createDiscipline(newDiscipline);
        System.out.println("Referral written for student #" + newDiscipline.getStudentId());
    }
    private void runUpdateReferral(){
        printHeading("Edit Referral");
        String lastName = getUserInput("Enter the last name of the student: ");
        List<Student> allStudents = jdbcStudentDao.getStudentsByLastName(lastName);
        if (allStudents.size() > 0 ) {
            System.out.println("Choose a student to write a referral for: ");
            Student selectedStudent = (Student) menu.getChoiceFromOptions(allStudents.toArray());

            Discipline selectedDiscipline = jdbcDisciplineDao.getDisciplineById(selectedStudent.getId());

            String newReferralDate = getUserInput("Enter the updated date (YYYY-MM-DD) (Leave blank to skip): ");
            String newDescription = getUserInput("Enter the updated description (Leave blank to skip): ");
            String newDisciplineAction = getUserInput("Enter the updated disciplinary action (Leave blank to skip): ");
            String newParentContacted = getUserInput("Was the parent contacted? (true or false) (Leave blank to skip): ");
            if (!newReferralDate.equals("")) {
                selectedDiscipline.setReferralDate(LocalDate.parse(newReferralDate));
            }
            if (!newDescription.equals("")) {
                selectedDiscipline.setDescription(newDescription);
            }
            if (!newDisciplineAction.equals("")) {
                selectedDiscipline.setDisciplineAction(newDisciplineAction);
            }
            if (!newParentContacted.equals("")) {
                selectedDiscipline.setParentContacted(Boolean.parseBoolean(newParentContacted));
            }
            selectedDiscipline = jdbcDisciplineDao.updateDiscipline(selectedDiscipline);
            System.out.println("Referral updated for student #" + selectedDiscipline.getStudentId());
        } else {
            System.out.println("No result");
        }
    }
    private void runDeleteReferral(){
        printHeading("Remove Referral");
        String lastName = getUserInput("Enter student's last name: ");
        List<Student> students = jdbcStudentDao.getStudentsByLastName(lastName);
        if(students.size() > 0 ) {
            System.out.println("Choose student to remove referral from: ");
            Student selectedStudent = (Student) menu.getChoiceFromOptions(students.toArray());
            Discipline selectedDiscipline = jdbcDisciplineDao.getDisciplineById(selectedStudent.getId());
            jdbcDisciplineDao.deleteDisciplineById(selectedDiscipline.getId());
            System.out.println("Referral removed for " + selectedStudent.getFirst_name() + " " + selectedStudent.getLast_name());
        } else {
            System.out.println("No results");
        }
    }
    private void listDiscipline(List<Discipline> referrals) {
        System.out.println();
        if (referrals.size() > 0) {
            for (Discipline referral : referrals) {
                System.out.println(referral);
            }
        } else {
            System.out.println("No results");
        }
    }
    private String getUserInput(String prompt) {
        System.out.print(prompt);
        return new Scanner(System.in).nextLine();
    }
    private void printHeading(String headingText) {
        System.out.println("\n"+headingText);
        for(int i = 0; i < headingText.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
