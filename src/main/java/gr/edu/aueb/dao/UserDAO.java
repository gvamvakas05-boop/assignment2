package gr.edu.aueb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import gr.edu.aueb.db.DBconnection; 
import mainpackage.Users; 

public class UserDAO {

    public Users authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        // Εκτύπωση στην κονσόλα για να δούμε αν το Servlet στέλνει σωστά τα στοιχεία
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
}