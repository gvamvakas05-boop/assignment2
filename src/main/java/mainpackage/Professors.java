package mainpackage;

import java.util.ArrayList;
import java.util.List;

// Η κλαση για τους καθηγητες που επισης κληρονομει απο την βασικη Users
public class Professors extends Users {
    private String professorId;
    private List<Courses> assignedCourses;
    
    public Professors(String username, String name, String surname, 
                     String department, String professorId) {
        super(username, name, surname, department);
        this.professorId = professorId;
        this.assignedCourses = new ArrayList<>();
    }
    
    public String getProfessorId() { return professorId; }
    public void setProfessorId(String id) { this.professorId = id; }
    
    public List<Courses> getCourses() { return assignedCourses; }
    
    // Συνδεουμε τον καθηγητη με ενα μαθημα για να ξερουμε οτι το διδασκει
    public void assignToCourse(Courses course) {
        assignedCourses.add(course);
        course.setProfessor(this);
        System.out.println("Ο Καθηγητής " + getName() + " ανατέθηκε στο μάθημα " + 
            course.getCourseName());
    }
    
    // Ο καθηγητης καταχωρει εναν βαθμο που πηρε ενας φοιτητης στο μαθημα του
    public void setGrades(Students student, Courses course, double grade) {
        Grades newGrade = new Grades(student, course, grade);
        student.addGrade(newGrade);
        System.out.println("Ο βαθμός " + grade + " καταχωρήθηκε για τον/την " + student.getName() + 
            " στο μάθημα " + course.getCourseName());
    }
    
    // Εδω ο καθηγητης μπορει να δει τη λιστα με τους βαθμους αποκλειστικα για το δικο του μαθημα
    public void viewGrades(Courses course) {
        if (!assignedCourses.contains(course)) {
            System.out.println("Σφάλμα: Δεν διδάσκετε αυτό το μάθημα.");
            return;
        }
        
        System.out.println("Βαθμολογίες για το μάθημα: " + course.getCourseName());
        // Αυτο το κομματι θα το χτισουμε κανονικα πιο αργα στην εργασια
        // Προς το παρον απλα δειχνουμε ενα μηνυμα για τη δοκιμη
        System.out.println("Εμφάνιση βαθμολογιών... (η λειτουργικότητα θα ολοκληρωθεί σύντομα)");
    }
}