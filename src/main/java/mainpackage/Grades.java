package mainpackage;

//Εδω αποθηκευουμε τον βαθμο που πηρε καποιος φοιτητης σε καποιο συγκεκριμενο μαθημα
public class Grades {
 private Students student;
 private Courses course;
 private double grade;
 
 public Grades() {}
 
 public Grades(Students student, Courses course, double grade) {
     this.student = student;
     this.course = course;
     this.grade = grade;
 }
 
 // Οι βασικοι getters και setters μας
 public Students getStudent() { return student; }
 public void setStudent(Students student) { this.student = student; }
 
 public Courses getCourse() { return course; }
 public void setCourse(Courses course) { this.course = course; }
 
 public double getGrade() { return grade; }
 public void setGrade(double grade) { this.grade = grade; }
}