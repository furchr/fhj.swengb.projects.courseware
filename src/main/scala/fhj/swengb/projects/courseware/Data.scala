package fhj.swengb.projects.courseware


object Data {

  val lecturerList : List[Lecturer] = List(
    Lecturer(0, "Ladstätter", "Robert", "SWENGB Exercise"),
    Lecturer(1, "Blauensteiner", "Johann", "SWENGB Theory")
  )

  val assessmentList : List[Assessment] = List (
    Assessment(0, "SWENGB theory exam #1", 1, 100, 60),
    Assessment(1, "SWENGB theory exam #2", 1, 100, 60),
    Assessment(2, "SWENGB theory exam #3", 1, 100, 60),
    Assessment(3, "SWENGB practical exam #1", 3, 100, 25),
    Assessment(4, "SWENGB practical exam #2", 3, 100, 25),
    Assessment(5, "SWENGB practical exam #3", 3, 100, 25),
    Assessment(6, "SWENGB project", 2, 40, 25)
  )

  val studentList : List[Student] = List(
    Student(1,"857699","Blazevic","Josip","IMA14"),
    Student(2,"478389","Fuchs","Michael","IMA14"),
    Student(3,"367426","Fuerbahs","Christoph","IMA14"),
    Student(4,"706388","Graf","Felix","IMA14"),
    Student(5,"375902","Hasenbichler","Timo","IMA14"),
    Student(6,"734524","Herzog","Carina","IMA14"),
    Student(7,"664334","Karimova","Elza","IMA14"),
    Student(8,"218989","Körner","Paul","IMA14"),
    Student(9,"330792","Lichtenegger","Alexander","IMA14"),
    Student(10,"181297","Schneider","Andreas","IMA14"),
    Student(11,"521358","Bajric","Amar","IMA14"),
    Student(12,"186252","Hoxha","Granit","IMA14"),
    Student(13,"737040","Hysi","Steven","IMA14"),
    Student(14,"311682","Kandlhofer","Daniel","IMA14"),
    Student(15,"918563","Knaller","Markus","IMA14"),
    Student(16,"380226","Lagger","Christian","IMA14"),
    Student(17,"301830","Leiter","Stefan","IMA14"),
    Student(18,"882795","Meizenitsch","Georg","IMA14"),
    Student(19,"972959","Skerbinz","Verena","IMA14"),
    Student(20,"883927","Steinkellner","Wolfgang","IMA14"),
    Student(21,"396777","Dirnbauer","Christoph","IMA14"),
    Student(22,"545960","Folk","Daniel","IMA14"),
    Student(23,"590872","Ortmann","Thomas","IMA14"),
    Student(24,"802045","Seebacher","Andreas","IMA14"),
    Student(25,"933923","Spalek","Nina","IMA14"),
    Student(26,"684708","Wageneder","Maximilian","IMA14"),
    Student(27,"580201","Yiliz","Marcel","IMA14"),
    Student(28,"514202","Zefferer","Lukas","IMA14"),
    Student(29,"488891","Zsifkovits","Markus","IMA14"),
    Student(30,"524329","Nguyen","Phuong","IMA14"),
    Student(31,"232535","Vidal","Hector","IMA14")
  )

  val gradeList : List[Grade] = List(

  )

}
