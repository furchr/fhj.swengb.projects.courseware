BEGIN TRANSACTION;
CREATE TABLE StudentClass(
studentClassID INT PRIMARY KEY NOT NULL,
studentClassName VARCHAR(10)
);
INSERT INTO `StudentClass` VALUES (1,'IMA15');
INSERT INTO `StudentClass` VALUES (2,'IMA14');
INSERT INTO `StudentClass` VALUES (3,'IMA13');
INSERT INTO `StudentClass` VALUES (4,'AIM15');
CREATE TABLE Student(
studentID INT PRIMARY KEY NOT NULL,
studentMatnum VARCHAR(50),
studentLastname VARCHAR(50),
studentFirstname VARCHAR(50),
studentClassID INT,
FOREIGN KEY(studentClassID) REFERENCES StudentClass(studentClassID)
);
INSERT INTO `Student` VALUES (1,'15J7639','Phuong','Nguyen',1);
INSERT INTO `Student` VALUES (2,'15J8372','Fuerbahs','Christoph',1);
INSERT INTO `Student` VALUES (3,'15J3564','Lichtenegger','Alexander',1);
CREATE TABLE Lecturetype(
lecturetypeID INT PRIMARY KEY NOT NULL,
lecturetypedescription VARCHAR(50)
);
INSERT INTO `Lecturetype` VALUES (1,'Lecture');
INSERT INTO `Lecturetype` VALUES (2,'Exercise');
INSERT INTO `Lecturetype` VALUES (3,'Theory Exam');
INSERT INTO `Lecturetype` VALUES (4,'Project');
CREATE TABLE Lecturer(
lecturerID INT PRIMARY KEY NOT NULL,
lecturerLastname VARCHAR(50),
lecturerFirstname VARCHAR(50),
lectureCourse VARCHAR(50)
);
INSERT INTO `Lecturer` VALUES (1,'Ladstaetter','Robert','SWENGB Exercise');
INSERT INTO `Lecturer` VALUES (2,'Blauensteiner','Johann','SWENGB Theory');
CREATE TABLE LectureAppointment(
lectureAppointmentID INT PRIMARY KEY NOT NULL,
lectureID INT,
studentID INT,
lecturerID INT,
lectureAppointmentStart DATETIME,
lectureAppointmentEnd DATETIME,
FOREIGN KEY(studentID) REFERENCES Student(studentID),
FOREIGN KEY(lectureID) REFERENCES Lecture(lectureID),
FOREIGN KEY(lecturerID) REFERENCES Lecturer(lecturerID)
);
INSERT INTO `LectureAppointment` VALUES (1,1,2,2,01/10/2015 09:00,01/10/2015 10:00);
INSERT INTO `LectureAppointment` VALUES (2,4,1,1,01/12/2015 10:00,31/01/2016 14:00);
CREATE TABLE Lecture(
lectureID INT PRIMARY KEY NOT NULL,
lectureSemester VARCHAR(50),
lecturetypeID INT,
FOREIGN KEY(lecturetypeID) REFERENCES Lecturetype(lecturetypeID)
);
INSERT INTO `Lecture` VALUES (1,'Winter1516',1);
INSERT INTO `Lecture` VALUES (2,'Winter1516',2);
INSERT INTO `Lecture` VALUES (3,'Winter1516',3);
INSERT INTO `Lecture` VALUES (4,'Winter1516',4);
CREATE TABLE Grade(
gradeID INT PRIMARY KEY NOT NULL,
assessmentID INT,
studentID INT,
gradeDate DATETIME,
gradePoints FLOAT,
FOREIGN KEY(studentID) REFERENCES Student(studentID),
FOREIGN KEY(assessmentID) REFERENCES Assessment(assessmentID)
);
INSERT INTO `Grade` VALUES (1,1,1,12/03/2016,75.4);
INSERT INTO `Grade` VALUES (2,2,3,12/03/2016,39.4);
CREATE TABLE Assessment(
assessmentID INT PRIMARY KEY NOT NULL,
assessmentName VARCHAR(50),
assessmentType INT,
assessmentMaxPoints FLOAT,
assessmentMinPointsForSufficiency FLOAT
);
INSERT INTO `Assessment` VALUES (1,'Theory Exam',1,100.0,60.0);
INSERT INTO `Assessment` VALUES (2,'Project
',2,40.0,25.0);
COMMIT;
