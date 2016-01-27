BEGIN TRANSACTION;

CREATE TABLE StudentClass(
studentClassID INT PRIMARY KEY NOT NULL,
studentClassName VARCHAR(10)
);

INSERT INTO `StudentClass` VALUES (1,'IMA15');
INSERT INTO `StudentClass` VALUES (2,'IMA14');
INSERT INTO `StudentClass` VALUES (3,'IMA13');
INSERT INTO `StudentClass` VALUES (4,'AIM15');
INSERT INTO `StudentClass` VALUES (5,'AIM14');

CREATE TABLE Student(
studentID INT PRIMARY KEY NOT NULL,
studentMatnum VARCHAR(50),
studentLastname VARCHAR(50),
studentFirstname VARCHAR(50),
studentClassID INT,
FOREIGN KEY(studentClassID) REFERENCES StudentClass(studentClassID)
);

INSERT INTO `Student` VALUES (1,'857699','Blazevic','Josip',2);
INSERT INTO `Student` VALUES (2,'478389','Fuchs','Michael',2);
INSERT INTO `Student` VALUES (3,'367426','Fuerbahs','Christoph',2);
INSERT INTO `Student` VALUES (4,'706388','Graf','Felix',2);
INSERT INTO `Student` VALUES (5,'375902','Hasenbichler','Timo',2);
INSERT INTO `Student` VALUES (6,'734524','Herzog','Carina',2);
INSERT INTO `Student` VALUES (7,'664334','Karimova','Elza',2)
INSERT INTO `Student` VALUES (8,'218989','Körner','Paul',2);
INSERT INTO `Student` VALUES (9,'330792','Lichtenegger','Alexander',2);
INSERT INTO `Student` VALUES (10,'181297','Schneider','Andreas',2);
INSERT INTO `Student` VALUES (11,'521358','Bajric','Amar',2);
INSERT INTO `Student` VALUES (12,'186252','Hoxha','Granit',2)
INSERT INTO `Student` VALUES (13,'737040','Hysi','Steven',2);
INSERT INTO `Student` VALUES (14,'311682','Kandlhofer','Daniel',2);
INSERT INTO `Student` VALUES (15,'918563','Knaller','Markus',2);
INSERT INTO `Student` VALUES (16,'380226','Lagger','Christian',2);
INSERT INTO `Student` VALUES (17,'301830','Leiter','Stefan',2);
INSERT INTO `Student` VALUES (18,'882795','Meizenitsch','Georg',2);
INSERT INTO `Student` VALUES (19,'972959','Skerbinz','Verena',2);
INSERT INTO `Student` VALUES (20,'883927','Steinkellner','Wolfgang',2);
INSERT INTO `Student` VALUES (21,'396777','Dirnbauer','Christoph',2);
INSERT INTO `Student` VALUES (22,'545960','Folk','Daniel',2);
INSERT INTO `Student` VALUES (23,'590872','Ortmann','Thomas',2);
INSERT INTO `Student` VALUES (24,'802045','Seebacher','Andreas',2);
INSERT INTO `Student` VALUES (25,'933923','Spalek','Nina',2);
INSERT INTO `Student` VALUES (26,'684708','Wageneder','Maximilian',2);
INSERT INTO `Student` VALUES (27,'580201','Yiliz','Marcel',2);
INSERT INTO `Student` VALUES (28,'514202','Zefferer','Lukas',2);
INSERT INTO `Student` VALUES (29,'488891','Zsifkovits','Markus',2);
INSERT INTO `Student` VALUES (30,'524329','Nguyen','Phuong',2);
INSERT INTO `Student` VALUES (31,'232535','Vidal','Hector',2);

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
INSERT INTO `LectureAppointment` VALUES (2,2,11,1,01/12/2015 10:00,31/01/2016 14:00);
INSERT INTO `LectureAppointment` VALUES (1,2,12,2,01/10/2015 10:00,01/10/2015 12:00);
INSERT INTO `LectureAppointment` VALUES (2,3,4,2,01/11/2015 09:30,01/11/2015 10:00);
INSERT INTO `LectureAppointment` VALUES (1,1,6,2,11/10/2015 10:00,11/10/2015 11:00);
INSERT INTO `LectureAppointment` VALUES (2,4,8,1,06/10/2015 08:00,06/10/2015 10:00);
INSERT INTO `LectureAppointment` VALUES (2,2,10,2,01/12/2015 15:00,01/12/2015 16:00);
INSERT INTO `LectureAppointment` VALUES (1,1,13,2,11/12/2015 10:00,11/12/2015 12:00);
INSERT INTO `LectureAppointment` VALUES (2,3,5,1,02/12/2015 09:00,02/12/2015 10:00);
INSERT INTO `LectureAppointment` VALUES (2,2,17,2,06/10/2015 09:00,06/10/2015 10:00);
INSERT INTO `LectureAppointment` VALUES (1,1,3,1,09/10/2015 09:00,09/10/2015 10:00);

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
INSERT INTO `Grade` VALUES (2,2,2,12/03/2016,39.4);
INSERT INTO `Grade` VALUES (3,1,3,14/03/2016,45.4);
INSERT INTO `Grade` VALUES (4,2,4,15/03/2016,37.5);
INSERT INTO `Grade` VALUES (5,1,5,15/03/2016,78.8);
INSERT INTO `Grade` VALUES (6,1,6,12/03/2016,86.9);
INSERT INTO `Grade` VALUES (7,2,7,13/03/2016,26.3);
INSERT INTO `Grade` VALUES (8,1,8,14/03/2016,60.3);
INSERT INTO `Grade` VALUES (9,2,9,12/03/2016,37.4);

CREATE TABLE Assessment(
assessmentID INT PRIMARY KEY NOT NULL,
assessmentName VARCHAR(50),
assessmentType INT,
assessmentMaxPoints FLOAT,
assessmentMinPointsForSufficiency FLOAT
);

INSERT INTO `Assessment` VALUES (1,'Theory Exam',1,100.0,60.0);
INSERT INTO `Assessment` VALUES (2,'Project',2,40.0,25.0);
INSERT INTO `Assessment` VALUES (3,'Practical Exam',1,100.0,25.0);

COMMIT;
