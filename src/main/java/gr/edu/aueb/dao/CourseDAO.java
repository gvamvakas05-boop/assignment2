package gr.edu.aueb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import gr.edu.aueb.db.DBconnection; 
import mainpackage.Courses; 
import mainpackage.Professors;

// Κλάση DAO για τη διαχείριση των μαθημάτων και των λειτουργιών ανάθεσης καθηγητών στη βάση δεδομένων.
public class CourseDAO {

    // Μέθοδος ανάκτησης όλων των μαθημάτων από τη βάση δεδομένων (Απαιτήσεις 3.4.2 και 3.4.3).
    public List<Courses> getAllCourses() {
        List<Courses> coursesList = new ArrayList<>();
        
        // Ερώτημα SQL με LEFT JOIN για την ανάκτηση του κωδικού, του τίτλου του μαθήματος και των στοιχείων του υπεύθυνου καθηγητή
        String query = "SELECT c.course_code, c.title, u.name AS prof_name, u.surname AS prof_surname " +
                       "FROM courses c " +
                       "LEFT JOIN users u ON c.professor_id = u.id";

        try (Connection conn = DBconnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String code = rs.getString("course_code");
                String title = rs.getString("title");
                String profName = rs.getString("prof_name");
                String profSurname = rs.getString("prof_surname");
                
                Courses course = new Courses(code, title);
                
                // Αν έχει ανατεθεί καθηγητής στο μάθημα, δημιουργείται το αντίστοιχο αντικείμενο Professors και συνδέεται με το μάθημα
                if (profName != null && profSurname != null) {
                    Professors prof = new Professors(null, profName, profSurname, null, null);
                    course.setProfessor(prof);
                }
                
                coursesList.add(course);
            }
        } catch (SQLException e) {
            System.err.println("--> ΣΦΑΛΜΑ SQL κατά την ανάκτηση όλων των μαθημάτων: " + e.getMessage());
            e.printStackTrace();
        }
        return coursesList;
    }

    // Μέθοδος για την ανάθεση καθηγητή σε ένα συγκεκριμένο μάθημα (Απαίτηση 3.4.4).
    public boolean assignProfessor(String courseCode, int professorId) {
        // Έλεγχος εγκυρότητας: Αν ο κωδικός του μαθήματος είναι κενός, η διαδικασία σταματά
        if (courseCode == null || courseCode.trim().isEmpty()) {
            System.err.println("--> ΣΦΑΛΜΑ: Προσπάθεια ανάθεσης καθηγητή με κενό κωδικό μαθήματος.");
            return false;
        }

        String query = "UPDATE courses SET professor_id = ? WHERE course_code = ?";
        
        try (Connection conn = DBconnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, professorId);
            ps.setString(2, courseCode.trim());
            
            int rowsUpdated = ps.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("--> ΕΠΙΤΥΧΙΑ: Ενημερώθηκε το μάθημα " + courseCode + " με καθηγητή ID: " + professorId);
                return true;
            } else {
                System.out.println("--> ΑΠΟΤΥΧΙΑ: Δεν βρέθηκε μάθημα με κωδικό " + courseCode + " για να γίνει η ανάθεση.");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("--> ΣΦΑΛΜΑ SQL κατά την ανάθεση καθηγητή (ID: " + professorId + ") στο μάθημα (" + courseCode + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}