package mainpackage;

import java.util.ArrayList;
import java.util.List;

// Η κλαση που κραταει ολες τις πληροφοριες για το καθε μαθημα του πανεπιστημιου
public class Courses {
    private String courseId;
    private String courseName;
    private Professors professor;
    private List<Students> enrolledStudents;
    
    public Courses() {
        this.enrolledStudents = new ArrayList<>();
    }
    
    public Courses(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.enrolledStudents = new ArrayList<>();
    }
    
    // Κλασικοι getters και setters για να εχουμε προσβαση στα πεδια της κλασης
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public Professors getProfessor() { return professor; }
    public void setProfessor(Professors professor) { this.professor = professor; }
    
    public List<Students> getStudents() { return enrolledStudents; }
    
    public void enrollStudent(Students student) {
        enrolledStudents.add(student);
    }
}