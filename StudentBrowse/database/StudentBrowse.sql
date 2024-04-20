START TRANSACTION;

DROP TABLE IF EXISTS student, parent, discipline, attendance, teacher, student_parent, student_discipline, student_teacher CASCADE;

CREATE TABLE student (
   student_id serial,
   first_name varchar(20) NOT NULL,
   last_name varchar(30) NOT NULL,
   address varchar(100) NOT NULL,
   grade int NOT NULL,
   CONSTRAINT PK_student PRIMARY KEY (student_id)
);

CREATE TABLE parent (
    parent_id serial,
    first_name varchar(20) NOT NULL,
    last_name varchar(30) NOT NULL,
    address varchar(100) NOT NULL,
    phone_number varchar(20) NULL,
    CONSTRAINT PK_parent PRIMARY KEY (parent_id)
);

CREATE TABLE discipline (
    discipline_id serial,
    student_id int NOT NULL,
    referral_date date NOT NULL,
    description varchar(500) NOT NULL,
    discipline_action varchar(100) NOT NULL,
    parent_contacted boolean NOT NULL,
    CONSTRAINT PK_discipline PRIMARY KEY (discipline_id),
    CONSTRAINT FK_student FOREIGN KEY (student_id) REFERENCES student(student_id)
);

CREATE TABLE attendance (
    attendance_id serial,
    student_id int NOT NULL,
    attendance_date date NOT NULL,
    present boolean NOT NULL,
    tardy boolean NOT NULL,
    CONSTRAINT PK_attendance PRIMARY KEY (attendance_id),
    CONSTRAINT FK_student FOREIGN KEY (student_id) REFERENCES student(student_id)
);

CREATE TABLE teacher (
    teacher_id serial,
    first_name varchar(20),
    last_name varchar(30),
    email varchar(100),
    CONSTRAINT PK_teacher PRIMARY KEY (teacher_id)
);

CREATE TABLE student_parent (
    student_id int NOT NULL,
    parent_id int NOT NULL,
    CONSTRAINT PK_student_parent PRIMARY KEY (student_id, parent_id),
    CONSTRAINT FK_student_parent_student FOREIGN KEY (student_id) REFERENCES student(student_id),
    CONSTRAINT FK_student_parent_parent FOREIGN KEY (parent_id) REFERENCES parent(parent_id)
);

CREATE TABLE student_teacher (
    student_id int NOT NULL,
    teacher_id int NOT NULL,
    CONSTRAINT PK_student_teacher PRIMARY KEY (student_id, teacher_id),
    CONSTRAINT FK_student_teacher_student FOREIGN KEY (student_id) REFERENCES student(student_id),
    CONSTRAINT FK_student_teacher_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);

INSERT INTO student (first_name, last_name, address, grade) VALUES ('Rory', 'Black', '123 Here Ln', 3);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Flynn', 'Black', '123 Here Ln', 5);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Janessa', 'Martin', '588 Winslow Dr', 0);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Carter', 'Martin', '588 Winslow Dr', 4);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Royce', 'Brooks', '5678 Par Dr', 1);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Gabrielle', 'Taylor', '7942 Golfview Dr', 5);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Oliver', 'Lewis', '646 Sangamon Ave', 2);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Andy', 'Smith', '293 Sangamon Ave', 0);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Liam', 'Roberts', '9274 Dawn Cir', 1);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Robert', 'Andrews', '4720 Lane Dr', 3);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Peyton', 'Michaels', '819 Oval Dr', 4);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Ava', 'White', '678 Oval Dr', 5);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Blake', 'Scott', '7943 Virginia Cir', 2);
INSERT INTO student (first_name, last_name, address, grade) VALUES ('Norah', 'Carlson', '7333 Virginia Cir', 4);


INSERT INTO parent (first_name, last_name, address) VALUES ('Lynda', 'Black', '123 Here Ln');
INSERT INTO parent (first_name, last_name, address) VALUES ('Clarissa', 'Martin', '588 Winslow Dr');
INSERT INTO parent (first_name, last_name, address) VALUES ('Abigail', 'Brooks', '5678 Par Dr');
INSERT INTO parent (first_name, last_name, address) VALUES ('Erin', 'Taylor', '7942 Golfview Dr');
INSERT INTO parent (first_name, last_name, address) VALUES ('Jamie', 'Lewis', '646 Sangamon Ave');
INSERT INTO parent (first_name, last_name, address) VALUES ('Samantha', 'Smith', '293 Sangamon Ave');
INSERT INTO parent (first_name, last_name, address) VALUES ('Isabelle', 'Roberts', '9274 Dawn Cir');
INSERT INTO parent (first_name, last_name, address) VALUES ('Trent', 'Andrews', '4720 Lane Dr');
INSERT INTO parent (first_name, last_name, address, phone_number) VALUES ('Blake', 'Michaels', '819 Oval Dr', 2178997456);
INSERT INTO parent (first_name, last_name, address, phone_number) VALUES ('Jordan',  'White', '678 Oval Dr', 7391943758);
INSERT INTO parent (first_name, last_name, address, phone_number) VALUES ('Marissa',  'White', '678 Oval Dr', 9365926587);
INSERT INTO parent (first_name, last_name, address, phone_number) VALUES ('Kyle', 'Scott', '7943 Virginia Cir', 74027467592);
INSERT INTO parent (first_name, last_name, address, phone_number) VALUES ('Shannon', 'Carlson', '7333 Virginia Cir', 9372226756);


INSERT INTO teacher (first_name, last_name, email) VALUES ('Lindsey', 'Pingsterhaus', 'lpingsterhaus@gmail.com');
INSERT INTO teacher (first_name, last_name, email) VALUES ('Samantha', 'Bean', 'sbean@gmail.com');
INSERT INTO teacher (first_name, last_name, email) VALUES ('Shelby', 'Kilpatrick', 'skilpatrick@gmail.com');
INSERT INTO teacher (first_name, last_name, email) VALUES ('Catie', 'Parrott', 'cparrott@gmail.com');
INSERT INTO teacher (first_name, last_name, email) VALUES ('April', 'Webb', 'awebb@gmail.com');
INSERT INTO teacher (first_name, last_name, email) VALUES ('Grace', 'Jackson', 'gjackson@gmail.com');


INSERT INTO discipline (student_id, referral_date, description, discipline_action, parent_contacted) VALUES (1, '2024-03-17', 'Punched teacher in the arm', 'Lunch/Recess Detention', true);
INSERT INTO discipline (student_id, referral_date, description, discipline_action, parent_contacted) VALUES (3, '2023-08-14', 'Stomped on classmates leg repeatedly', 'Suspension', true);
INSERT INTO discipline (student_id, referral_date, description, discipline_action, parent_contacted) VALUES (1, '2024-03-23', 'Yelled I dont like you, bitch at teacher', 'Office visit', false);
INSERT INTO discipline (student_id, referral_date, description, discipline_action, parent_contacted) VALUES (8, '2024-01-18', 'Hit classmates and staff 174 times today', 'Phone call home', true);
INSERT INTO discipline (student_id, referral_date, description, discipline_action, parent_contacted) VALUES (8, '2024-05-21', 'Bit another student in the arm, drawing blood', 'Sent to CARE', true);
INSERT INTO discipline (student_id, referral_date, description, discipline_action, parent_contacted) VALUES (10, '2023-12-01', 'Got into a physical altercation with another student at recess', 'Suspension', true);


INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (1, '2023-08-21', true, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (1, '2023-08-22', true, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (1, '2023-08-23', false, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (1, '2023-08-25', true, true);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (2, '2023-08-21', true, true);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (2, '2023-08-22', true, true);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (2, '2023-08-23', true, true);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (3, '2023-08-23', false, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (3, '2023-08-25', false, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (3, '2023-08-26', false, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (4, '2023-08-23', true, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (4, '2023-08-25', true, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (4, '2023-08-26', true, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (5, '2023-08-23', true, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (5, '2023-08-25', true, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (5, '2023-08-26', true, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (6, '2023-08-23', false, false);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (6, '2023-08-25', true, true);
INSERT INTO attendance (student_id, attendance_date, present, tardy) VALUES (6, '2023-08-26', false, false);


INSERT INTO student_parent (student_id, parent_id) VALUES (1, 1);
INSERT INTO student_parent (student_id, parent_id) VALUES (2, 1);
INSERT INTO student_parent (student_id, parent_id) VALUES (3, 2);
INSERT INTO student_parent (student_id, parent_id) VALUES (4, 2);
INSERT INTO student_parent (student_id, parent_id) VALUES (5, 3);
INSERT INTO student_parent (student_id, parent_id) VALUES (6, 4);
INSERT INTO student_parent (student_id, parent_id) VALUES (7, 5);
INSERT INTO student_parent (student_id, parent_id) VALUES (8, 6);
INSERT INTO student_parent (student_id, parent_id) VALUES (9, 7);
INSERT INTO student_parent (student_id, parent_id) VALUES (10, 8);
INSERT INTO student_parent (student_id, parent_id) VALUES (11, 9);
INSERT INTO student_parent (student_id, parent_id) VALUES (12, 10);
INSERT INTO student_parent (student_id, parent_id) VALUES (12, 11);
INSERT INTO student_parent (student_id, parent_id) VALUES (13, 12);
INSERT INTO student_parent (student_id, parent_id) VALUES (14, 13);


INSERT INTO student_teacher (student_id, teacher_id) VALUES (3, 1);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (8, 1);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (5, 2);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (9, 2);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (7, 3);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (13, 3);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (1, 4);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (10, 4);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (4, 5);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (11, 5);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (14, 5);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (2, 6);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (6, 6);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (12, 6);

COMMIT;


