#Courseware Scala App

![<fhLogo>](<https://www.fh-joanneum.at/custom/images/logo_1001.png>)

![<fhIma>](<http://www.fh-joanneum.at/global/show_picture.asp?id=aaaaaaaaaajahgt>)

by [Christoph Fürbahs](http://www.fuerbahs.com), Alexander Lichtenegger, Phuong Nguyen

#General Information
Courseware is a Management System for our Software Engineering Basics Course with Mr Robert Ladstätter.
The app is developed in Scala with a SQLite database.
The main goals are:
- Managing the data of
	- students
	- lecturers
	- grades
	- assignments

#Application

The Application starts with loading the sqlite database.
Then you can switch between different tabs in the upper area of the app.

##Lecuturer Tab

Here you can enter the

    - Lecturer Name
    - Assessment
    - Student
    - Points for an Exam

By clicking "Insert Grade" you can save the information in the database.

#Student Tab

Here you can enter the

    - Student
    - Student Name
    - Student Number
    - Grades

By clicking on view you can view all grades from one specified student.
By clicking on the "Save as HTML" you can export the grades of the selected student in an HTML file in the project root folder.

[FH Joanneum IMA](http://www.fh-joanneum.at/ima/?lan=en)

last modified 2016-01-27

########################
