#fhj.swengb.projects.courseware

Courseware Scala App from Fürbahs, Lichtenegger, Nguyen

last modified 2016-01-02 23:06

#Todo

##Part 1

- Please have a look at for example https://github.com/tortmann/fhj.swengb.homework.dbtool/blob/master/src/main/scala/fhj/swengb/homework/dbtool/DbTool.scala
 - Thomas Ortmann has the best database "master" database implementation I found until now.
  - one line in the codefile DbTool.scala from Mr Ortmann defines what database connection we have to use
  - `lazy val maybeConnection: Try[Connection] = Try(DriverManager.getConnection("jdbc:sqlite::memory:"))`
- we should use
 - jdbc => https://en.wikipedia.org/wiki/Java_Database_Connectivity
 - sqlite => http://www.sqlite.org
  - memory (as far is i remember) is a database mode of sqlite.
  - there the database is created on the fly only in the RAM of your computer.
  - Mr Ladstätter chose this "mode" because he wants us to avoid any complex database connections.
  - So there is no database stored on github, our computers or on any server.
  - The database only exists as long as our programm is running on our computer.

##Part 2

- GUI
- ...
