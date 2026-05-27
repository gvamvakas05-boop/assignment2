package gr.edu.aueb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import gr.edu.aueb.db.DBconnection; 
import mainpackage.Users; 
import mainpackage.Professors;

// Κλάση DAO για τη διαχείριση των χρηστών και των λειτουργιών σύνδεσης και ανάκτησης από τη βάση δεδομένων.
public class UserDAO {

    // Μέθοδος αυθεντικοποίησης για τη σύνδεση της γραμματείας (Απαίτηση 3.4.1).
    public Users authenticate(String username, String password) {
        // Ανάκτηση του ρόλου από τη βάση για τον έλεγχο δικαιωμάτων πρόσβασης στο dashboard της γραμματείας
        String query = "SELECT username, name, surname, department, role FROM users WHERE username = ? AND password = ?";
        
        System.out.println("--> Προσπάθεια Login με Username: [" + username + "] και Password: [" + password + "]");
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    
                    // Έλεγχος δικαιωμάτων: Επιτρέπεται η είσοδος μόνο αν ο ρόλος είναι 'secretary'
                    if (!"secretary".equalsIgnoreCase(role)) {
                        System.out.println("--> ΑΠΟΡΡΙΨΗ: Ο χρήστης βρέθηκε στη βάση, αλλά δεν ανήκει στη Γραμματεία. Ρόλος: " + role);
                        return null;
                    }
                    
                    System.out.println("--> ΕΠΙΤΥΧΙΑ: Βρέθηκε έγκυρος χρήστης Γραμματείας στη βάση δεδομένων.");
                    
                    // Επιστροφή αντικειμένου Users συμπληρωμένου με τα στοιχεία από τη βάση για αποθήκευση στο Session
                    return new Users(
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("department")
                    );
                } else {
                    System.out.println("--> ΑΠΟΤΥΧΙΑ: Λάθος username ή password στη βάση δεδομένων.");
                }
            }
        } catch (SQLException e) {
            System.out.println("--> ΣΦΑΛΜΑ SQL κατά την αυθεντικοποίηση: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Ανάκτηση όλων των εγγεγραμμένων καθηγητών από τη βάση για τη συμπλήρωση των επιλογών στο UI
    public List<Professors> getAllProfessors() {
        List<Professors> professorsList = new ArrayList<>();
        
        String query = "SELECT id, username, name, surname, department FROM users WHERE role = 'professor'";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String dept = rs.getString("department");
                
                // Μετατροπή του ID σε String για τη διευκόλυνση της διαχείρισης στις φόρμες HTML
                Professors prof = new Professors(username, name, surname, dept, String.valueOf(id));
                professorsList.add(prof);
            }
            
        } catch (SQLException e) {
            System.out.println("--> ΣΦΑΛΜΑ κατά την ανάκτηση καθηγητών: " + e.getMessage());
            e.printStackTrace();
        }
        
        return professorsList;
    }
}