package mainpackage;

import java.util.ArrayList;
import java.util.List;

// Η γραμματεια του πανεπιστημιου που εχει τον ελεγχο για τις εγγραφες και τα μαθηματα
public class Secretaries extends Users {
    private String secretaryId;
    private static List<Students> allStudents = new ArrayList<>();
    private static List<Professors> allProfessors = new ArrayList<>();
    private static List<Courses> allCourses = new ArrayList<>();
    
    public Secretaries(String username, String name, String surname, 
                      String department, String secretaryId) {
        super(username, name, surname, department);
        this.secretaryId = secretaryId;
    }
    
    // Λειτουργιες για να βαζουμε νεους φοιτητες καθηγητες και μαθηματα μεσα στο συστημα
    public Students registerStudent(String username, String name, String surname, 
                                   String department, int regNumber) {
        Students student = new Students(username, name, surname, department, regNumber);
        allStudents.add(student);
        System.out.println("Εγγράφηκε ο/η φοιτητής/τρια: " + name + " " + surname);
        return student;
    }
    
    public Professors registerProfessor(String username, String name, String surname, 
                                       String department, String profId) {
        Professors prof = new Professors(username, name, surname, department, profId);
        allProfessors.add(prof);
        System.out.println("Εγγράφηκε ο καθηγητής: " + name + " " + surname);
        return prof;
    }
    
    public Courses registerCourse(String courseId, String courseName) {
        Courses course = new Courses(courseId, courseName);
        allCourses.add(course);
        System.out.println("Εγγράφηκε το μάθημα: " + courseName);
        return course;
    }
    
    // Η γραμματεια αποφασιζει ποιος καθηγητης θα παρει ποιο μαθημα
    public void assignProfessorToCourse(Professors prof, Courses course) {
        prof.assignToCourse(course);
    }
    
    // Ανοιγουμε τη λιστα βαθμολογιων για ενα μαθημα ωστε να ειναι ετοιμη να γεμισει
    public List<Grades> createGradeList(Courses course) {
        List<Grades> gradeList = new ArrayList<>();
        System.out.println("Δημιουργήθηκε λίστα βαθμολογιών για το μάθημα: " + course.getCourseName());
        return gradeList;
    }
    
    // Απλοι getters για να μπορουμε να τραβαμε τις λιστες μας απο αλλου
    public static List<Students> getAllStudents() { return allStudents; }
    public static List<Professors> getAllProfessors() { return allProfessors; }
    public static List<Courses> getAllCourses() { return allCourses; }
}