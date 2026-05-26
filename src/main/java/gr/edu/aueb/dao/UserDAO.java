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

public class UserDAO {

    /**
     * Authenticates a user based on username and password (Fulfills 3.4.1)
     */
    public Users authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        System.out.println("--> Προσπάθεια Login με Username: [" + username + "] και Password: [" + password + "]");
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("--> ΕΠΙΤΥΧΙΑ: Ο χρήστης βρέθηκε στη βάση δεδομένων!");
                    return new Users();
                } else {
                    System.out.println("--> ΑΠΟΤΥΧΙΑ: Η MySQL επέστρεψε 0 γραμμές για αυτά τα στοιχεία.");
                }
            }
        } catch (SQLException e) {
            System.out.println("--> ΣΦΑΛΜΑ SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Dynamically pulls all registered professors from the database 
     * to populate UI dropdown selection components.
     * * NOTE: If your database uses a different filtering column name 
     * (e.g., user_type, status, etc.), adjust the WHERE clause accordingly.
     */
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
                
                // We pass the integer ID converted to a String inside the professorId field 
                // so we can read it easily inside our HTML forms
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