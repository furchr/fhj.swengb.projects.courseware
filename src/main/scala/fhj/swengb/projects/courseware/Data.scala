package fhj.swengb.projects.courseware


object Data {

  val lecturerList : List[Lecturer] = List(
    Lecturer(0, "Ladstätter", "Robert", "SWENGB"),
    Lecturer(1, "Blaueinsteiner", "Johann", "SWENGB"),
    Lecturer(2, "Zinser", "Erwin", "ITISD")
  )

  val assessmentList : List[Assessment] = List (
    Assessment(0, "1. SWENGB exam", 0, 100, 60),
    Assessment(1, "2. SWENGB exam", 0, 100, 60),
    Assessment(2, "1. ITISD exam", 0, 200, 120)
  )

  val studentList : List[Student] = List(
    Student(0, "0000000013142", "Huber", "Josef", "IMA14"),
    Student(1, "0000003314200", "Maier", "Hans", "IMA14"),
    Student(2, "0000003314200", "Müller", "Fritz", "IMA14"),
    Student(3, "0000003314200", "Schmied", "Klaus", "IMA14"),
    Student(4, "0072432552349", "Gruber", "Lukas", "IMA14")
  )

  val gradeList : List[Grade] = List(

  )

}
